package com.survey.v1.modules.answer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.survey.v1.models.AnswerValue;
import com.survey.v1.models.Question;
import com.survey.v1.models.QuestionOption;
import com.survey.v1.models.Response;
import com.survey.v1.models.Sequence;
import com.survey.v1.modules.answer.dto.AnswerValueCreateDTO;
import com.survey.v1.modules.answer.dto.ResponseCreateDTO;
import com.survey.v1.repositories.AnswerValueRepository;
import com.survey.v1.repositories.QuestionOptionRepository;
import com.survey.v1.repositories.QuestionRepository;
import com.survey.v1.repositories.ResponseRepository;
import com.survey.v1.repositories.SequenceRepository;
import com.survey.v1.utils.LoggerUtils;

@DataJpaTest
@Import(AnswerService.class)
public class AnswerServiceIntegrationTest {

    private final LoggerUtils logger = new LoggerUtils(AnswerServiceIntegrationTest.class);

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private AnswerValueRepository answerValueRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private AnswerService answerService;

    private UUID sequenceId;
    private UUID questionId1;
    private UUID questionId2;
    private UUID optionId;
    private ResponseCreateDTO responseCreateDTO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up test data...");

        Sequence sequence = new Sequence();
        sequence = sequenceRepository.save(sequence);
        sequenceId = sequence.getId();
        logger.info("Created sequence with ID: " + sequenceId);

        Question question1 = new Question();
        question1.setTitle("Multiple Choice Question");
        question1.setType("multiple_choice");
        question1.setPageNumber(1);
        question1.setSequence(sequence);
        question1 = questionRepository.save(question1);
        questionId1 = question1.getId();
        logger.info("Created question 1 with ID: " + questionId1);

        QuestionOption option = new QuestionOption();
        option.setOptionText("Option 1");
        option.setOptionValue("1");
        option.setOrderNum(1);
        option.setQuestion(question1);
        option = questionOptionRepository.save(option);
        optionId = option.getId();
        logger.info("Created option with ID: " + optionId);

        Question question2 = new Question();
        question2.setTitle("Text Question");
        question2.setType("text");
        question2.setPageNumber(1);
        question2.setSequence(sequence);
        question2 = questionRepository.save(question2);
        questionId2 = question2.getId();
        logger.info("Created question 2 with ID: " + questionId2);

        responseCreateDTO = new ResponseCreateDTO();
        responseCreateDTO.setIpAddress("192.168.1.1");
        responseCreateDTO.setUserAgent("Mozilla/5.0 Integration Test");

        List<AnswerValueCreateDTO> answers = new ArrayList<>();

        AnswerValueCreateDTO answerWithOption = new AnswerValueCreateDTO();
        answerWithOption.setQuestionId(questionId1);
        answerWithOption.setOptionId(optionId);
        answers.add(answerWithOption);

        AnswerValueCreateDTO answerWithText = new AnswerValueCreateDTO();
        answerWithText.setQuestionId(questionId2);
        answerWithText.setTextValue("This is a text answer");
        answers.add(answerWithText);

        AnswerValueCreateDTO answerWithNumber = new AnswerValueCreateDTO();
        answerWithNumber.setQuestionId(questionId2);
        answerWithNumber.setNumericValue(new BigDecimal("123.45"));
        answers.add(answerWithNumber);

        responseCreateDTO.setAnswers(answers);
        
        logger.info("Test data setup completed");
    }

    @Test
    void testSaveResponseSuccess() {
        logger.info("Testing saveResponse success case");

        Response savedResponse = answerService.saveResponse(sequenceId.toString(), responseCreateDTO);

        assertNotNull(savedResponse);
        assertNotNull(savedResponse.getId());
        assertEquals(responseCreateDTO.getIpAddress(), savedResponse.getIpAddress());
        assertEquals(responseCreateDTO.getUserAgent(), savedResponse.getUserAgent());
        assertEquals(sequenceId, savedResponse.getSequence().getId());

        assertNotNull(savedResponse.getAnswerValues());
        assertEquals(responseCreateDTO.getAnswers().size(), savedResponse.getAnswerValues().size());
        
        logger.info("Saved response ID: " + savedResponse.getId() + " with " + savedResponse.getAnswerValues().size() + " answers");

        boolean foundOptionAnswer = false;
        boolean foundTextAnswer = false;
        boolean foundNumericAnswer = false;

        for (AnswerValue answer : savedResponse.getAnswerValues()) {
            if (answer.getQuestion().getId().equals(questionId1) && answer.getOption() != null) {
                foundOptionAnswer = true;
                assertEquals(optionId, answer.getOption().getId());
                logger.info("Found option answer: question=" + answer.getQuestion().getId() + ", option=" + answer.getOption().getId());
            } else if (answer.getQuestion().getId().equals(questionId2) && answer.getTextValue() != null) {
                foundTextAnswer = true;
                assertEquals("This is a text answer", answer.getTextValue());
                logger.info("Found text answer: question=" + answer.getQuestion().getId() + ", text=" + answer.getTextValue());
            } else if (answer.getQuestion().getId().equals(questionId2) && answer.getNumericValue() != null) {
                foundNumericAnswer = true;
                assertEquals(0, new BigDecimal("123.45").compareTo(answer.getNumericValue()));
                logger.info("Found numeric answer: question=" + answer.getQuestion().getId() + ", value=" + answer.getNumericValue());
            }
        }

        assertTrue(foundOptionAnswer, "Option answer should be saved");
        assertTrue(foundTextAnswer, "Text answer should be saved");
        assertTrue(foundNumericAnswer, "Numeric answer should be saved");
    }

    @Test
    void testSaveResponseInvalidSequenceId() {
        logger.info("Testing saveResponse with invalid sequence ID");

        UUID nonExistentId = UUID.randomUUID();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            answerService.saveResponse(nonExistentId.toString(), responseCreateDTO);
        });

        assertEquals("Sequence not found", exception.getMessage());
        logger.info("Exception thrown as expected: " + exception.getMessage());
    }

    @Test
    void testSaveResponseInvalidQuestionId() {
        logger.info("Testing saveResponse with invalid question ID");

        ResponseCreateDTO invalidResponseDTO = new ResponseCreateDTO();
        invalidResponseDTO.setIpAddress("192.168.1.1");
        invalidResponseDTO.setUserAgent("Mozilla/5.0 Test");

        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        AnswerValueCreateDTO invalidAnswer = new AnswerValueCreateDTO();
        invalidAnswer.setQuestionId(UUID.randomUUID());
        invalidAnswer.setTextValue("Test answer");
        answers.add(invalidAnswer);

        invalidResponseDTO.setAnswers(answers);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            answerService.saveResponse(sequenceId.toString(), invalidResponseDTO);
        });

        assertEquals("Question not found", exception.getMessage());
        logger.info("Exception thrown as expected: " + exception.getMessage());
    }

    @Test
    void testSaveResponseInvalidOptionId() {
        logger.info("Testing saveResponse with invalid option ID");

        ResponseCreateDTO invalidResponseDTO = new ResponseCreateDTO();
        invalidResponseDTO.setIpAddress("192.168.1.1");
        invalidResponseDTO.setUserAgent("Mozilla/5.0 Test");

        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        AnswerValueCreateDTO invalidAnswer = new AnswerValueCreateDTO();
        invalidAnswer.setQuestionId(questionId1);
        invalidAnswer.setOptionId(UUID.randomUUID());
        answers.add(invalidAnswer);

        invalidResponseDTO.setAnswers(answers);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            answerService.saveResponse(sequenceId.toString(), invalidResponseDTO);
        });

        assertEquals("Option not found", exception.getMessage());
        logger.info("Exception thrown as expected: " + exception.getMessage());
    }
}