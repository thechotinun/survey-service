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

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.exceptions.SequenceException;
import com.survey.v1.models.Question;
import com.survey.v1.models.QuestionOption;
import com.survey.v1.models.Response;
import com.survey.v1.models.Sequence;
import com.survey.v1.modules.answer.dto.AnswerValueCreateDTO;
import com.survey.v1.modules.answer.dto.ResponseCreateDTO;
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
    private SequenceRepository sequenceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private AnswerService answerService;

    private Sequence testSequence;
    private UUID testSequenceId;
    private Question testQuestion;
    private UUID testQuestionId;
    private QuestionOption testOption;
    private UUID testOptionId;
    private ResponseCreateDTO validResponseDTO;

    @BeforeEach
    void setUp() {
        logger.info("Setting up test data...");
        
        // Create and save a Sequence
        testSequence = new Sequence();
        testSequence = sequenceRepository.save(testSequence);
        testSequenceId = testSequence.getId();
        logger.info("Created sequence with ID: " + testSequenceId);
        
        // Create and save a Question
        testQuestion = new Question();
        testQuestion.setTitle("Test Question");
        testQuestion.setType("multiple_choice");
        testQuestion.setPageNumber(1);
        testQuestion.setSequence(testSequence);
        testQuestion = questionRepository.save(testQuestion);
        testQuestionId = testQuestion.getId();
        
        // Create and save a QuestionOption
        testOption = new QuestionOption();
        testOption.setOptionText("Test Option");
        testOption.setOptionValue("1");
        testOption.setOrderNum(1);
        testOption.setQuestion(testQuestion);
        testOption = questionOptionRepository.save(testOption);
        testOptionId = testOption.getId();
        
        // Create a valid ResponseCreateDTO
        validResponseDTO = new ResponseCreateDTO();
        validResponseDTO.setIpAddress("192.168.1.1");
        validResponseDTO.setUserAgent("Mozilla/5.0 Integration Test");
        
        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        
        // Create an answer with option
        AnswerValueCreateDTO answerWithOption = new AnswerValueCreateDTO();
        answerWithOption.setQuestionId(testQuestionId);
        answerWithOption.setOptionId(testOptionId);
        answers.add(answerWithOption);
        
        // Create an answer with text
        AnswerValueCreateDTO answerWithText = new AnswerValueCreateDTO();
        answerWithText.setQuestionId(testQuestionId);
        answerWithText.setTextValue("Text answer");
        answers.add(answerWithText);
        
        // Create an answer with numeric value
        AnswerValueCreateDTO answerWithNumeric = new AnswerValueCreateDTO();
        answerWithNumeric.setQuestionId(testQuestionId);
        answerWithNumeric.setNumericValue(new BigDecimal("42.5"));
        answers.add(answerWithNumeric);
        
        validResponseDTO.setAnswers(answers);
        
        // Clear any existing data to avoid interference
        responseRepository.deleteAll();
        
        logger.info("Test setup completed successfully");
    }

    @Test
    void testSaveResponseSuccess() {
        logger.info("Testing saveResponse success case");
        
        // Call the service method
        Response savedResponse = answerService.saveResponse(testSequenceId.toString(), validResponseDTO);
        
        // Verify the response was saved
        assertNotNull(savedResponse);
        assertNotNull(savedResponse.getId());
        assertEquals(validResponseDTO.getIpAddress(), savedResponse.getIpAddress());
        assertEquals(validResponseDTO.getUserAgent(), savedResponse.getUserAgent());
        
        // Verify the answer values were saved
        assertNotNull(savedResponse.getAnswerValues());
        assertEquals(3, savedResponse.getAnswerValues().size());
    }

    @Test
    void testSaveResponseInvalidSequenceId() {
        logger.info("Testing saveResponse with invalid sequence ID");
        
        // Create a random sequence ID that doesn't exist
        UUID nonExistentId = UUID.randomUUID();
        
        // Call the service method and expect an exception
        SequenceException exception = assertThrows(SequenceException.class, () -> {
            answerService.saveResponse(nonExistentId.toString(), validResponseDTO);
        });
        
        // Verify the exception details
        assertEquals("Sequence not found with id: " + nonExistentId, exception.getMessage());
        assertEquals(SequenceException.ErrorCode.SEQUENCE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testSaveResponseInvalidQuestionId() {
        logger.info("Testing saveResponse with invalid question ID");
        
        // Create a response DTO with an invalid question ID
        ResponseCreateDTO invalidResponseDTO = new ResponseCreateDTO();
        invalidResponseDTO.setIpAddress("192.168.1.1");
        invalidResponseDTO.setUserAgent("Mozilla/5.0 Test");
        
        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        AnswerValueCreateDTO invalidAnswer = new AnswerValueCreateDTO();
        invalidAnswer.setQuestionId(UUID.randomUUID()); // Random UUID that doesn't exist
        invalidAnswer.setTextValue("Text answer");
        answers.add(invalidAnswer);
        
        invalidResponseDTO.setAnswers(answers);
        
        // Call the service method and expect an exception
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            answerService.saveResponse(testSequenceId.toString(), invalidResponseDTO);
        });
        
        // Verify the exception details
        assertTrue(exception.getMessage().contains("No questions found for question id:"));
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testSaveResponseInvalidOptionId() {
        logger.info("Testing saveResponse with invalid option ID");
        
        // Create a response DTO with an invalid option ID
        ResponseCreateDTO invalidResponseDTO = new ResponseCreateDTO();
        invalidResponseDTO.setIpAddress("192.168.1.1");
        invalidResponseDTO.setUserAgent("Mozilla/5.0 Test");
        
        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        AnswerValueCreateDTO invalidAnswer = new AnswerValueCreateDTO();
        invalidAnswer.setQuestionId(testQuestionId);
        invalidAnswer.setOptionId(UUID.randomUUID()); // Random UUID that doesn't exist
        answers.add(invalidAnswer);
        
        invalidResponseDTO.setAnswers(answers);
        
        // Call the service method and expect an exception
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            answerService.saveResponse(testSequenceId.toString(), invalidResponseDTO);
        });
        
        // Verify the exception details
        assertTrue(exception.getMessage().contains("No options found for option id:"));
        assertEquals(QuestionException.ErrorCode.QUESTION_OPTION_NOT_FOUND, exception.getErrorCode());
    }
}