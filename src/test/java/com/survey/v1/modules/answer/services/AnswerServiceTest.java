package com.survey.v1.modules.answer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.exceptions.SequenceException;
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

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private ResponseRepository responseRepository;

    @Mock
    private AnswerValueRepository answerValueRepository;

    @Mock
    private SequenceRepository sequenceRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionOptionRepository questionOptionRepository;

    @InjectMocks
    private AnswerService answerService;

    private String sequenceIdStr;
    private UUID sequenceId;
    private UUID questionId;
    private UUID optionId;
    private Sequence sequence;
    private Question question;
    private QuestionOption option;
    private ResponseCreateDTO responseCreateDTO;
    private Response savedResponse;

    @BeforeEach
    void setUp() {
        sequenceIdStr = "123e4567-e89b-12d3-a456-426614174000";
        sequenceId = UUID.fromString(sequenceIdStr);
        questionId = UUID.randomUUID();
        optionId = UUID.randomUUID();

        sequence = new Sequence();
        sequence.setId(sequenceId);

        question = new Question();
        question.setId(questionId);
        question.setTitle("Test Question");
        question.setType("multiple_choice");

        option = new QuestionOption();
        option.setId(optionId);
        option.setOptionText("Test Option");
        option.setQuestion(question);

        responseCreateDTO = new ResponseCreateDTO();
        responseCreateDTO.setIpAddress("192.168.1.1");
        responseCreateDTO.setUserAgent("Mozilla/5.0 Test");
        
        List<AnswerValueCreateDTO> answers = new ArrayList<>();
        
        AnswerValueCreateDTO answerWithOption = new AnswerValueCreateDTO();
        answerWithOption.setQuestionId(questionId);
        answerWithOption.setOptionId(optionId);
        answers.add(answerWithOption);
        
        AnswerValueCreateDTO answerWithText = new AnswerValueCreateDTO();
        answerWithText.setQuestionId(questionId);
        answerWithText.setTextValue("Test text answer");
        answers.add(answerWithText);
        
        AnswerValueCreateDTO answerWithNumber = new AnswerValueCreateDTO();
        answerWithNumber.setQuestionId(questionId);
        answerWithNumber.setNumericValue(new BigDecimal("42.5"));
        answers.add(answerWithNumber);
        
        responseCreateDTO.setAnswers(answers);

        savedResponse = new Response();
        savedResponse.setId(1L);
        savedResponse.setSequence(sequence);
        savedResponse.setIpAddress(responseCreateDTO.getIpAddress());
        savedResponse.setUserAgent(responseCreateDTO.getUserAgent());
    }

    @Test
    void testSaveResponseSuccess() {
        when(sequenceRepository.findById(sequenceId)).thenReturn(Optional.of(sequence));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionOptionRepository.findById(optionId)).thenReturn(Optional.of(option));
        when(responseRepository.save(any(Response.class))).thenReturn(savedResponse);

        Response result = answerService.saveResponse(sequenceIdStr, responseCreateDTO);

        assertNotNull(result);
        assertEquals(savedResponse.getId(), result.getId());
        assertEquals(savedResponse.getIpAddress(), result.getIpAddress());
        assertEquals(savedResponse.getUserAgent(), result.getUserAgent());
        
        verify(sequenceRepository, times(1)).findById(sequenceId);
        verify(questionRepository, times(3)).findById(questionId);
        verify(questionOptionRepository, times(1)).findById(optionId);
        verify(responseRepository, times(1)).save(any(Response.class));
    }

    @Test
    void testSaveResponseSequenceNotFound() {
        when(sequenceRepository.findById(sequenceId)).thenReturn(Optional.empty());

        SequenceException exception = assertThrows(SequenceException.class, () -> {
            answerService.saveResponse(sequenceIdStr, responseCreateDTO);
        });

        assertEquals("Sequence not found with id: " + sequenceIdStr, exception.getMessage());
        assertEquals(SequenceException.ErrorCode.SEQUENCE_NOT_FOUND, exception.getErrorCode());
        
        verify(sequenceRepository, times(1)).findById(sequenceId);
        verify(responseRepository, times(0)).save(any(Response.class));
    }

    @Test
    void testSaveResponseQuestionNotFound() {
        when(sequenceRepository.findById(sequenceId)).thenReturn(Optional.of(sequence));
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        QuestionException exception = assertThrows(QuestionException.class, () -> {
            answerService.saveResponse(sequenceIdStr, responseCreateDTO);
        });

        assertEquals("No questions found for question id: " + questionId, exception.getMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
        
        verify(sequenceRepository, times(1)).findById(sequenceId);
        verify(questionRepository, times(1)).findById(questionId);
        verify(responseRepository, times(0)).save(any(Response.class));
    }

    @Test
    void testSaveResponseOptionNotFound() {
        when(sequenceRepository.findById(sequenceId)).thenReturn(Optional.of(sequence));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionOptionRepository.findById(optionId)).thenReturn(Optional.empty());

        QuestionException exception = assertThrows(QuestionException.class, () -> {
            answerService.saveResponse(sequenceIdStr, responseCreateDTO);
        });

        assertEquals("No options found for option id: " + questionId, exception.getMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_OPTION_NOT_FOUND, exception.getErrorCode());
        
        verify(sequenceRepository, times(1)).findById(sequenceId);
        verify(questionRepository, times(1)).findById(questionId);
        verify(questionOptionRepository, times(1)).findById(optionId);
        verify(responseRepository, times(0)).save(any(Response.class));
    }
}