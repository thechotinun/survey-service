package com.survey.v1.modules.question.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.models.Sequence;
import com.survey.v1.models.response.PagedResponse;
import com.survey.v1.modules.question.resources.QuestionResource;
import com.survey.v1.repositories.QuestionRepository;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private UUID testSequenceId;
    private List<Question> testQuestions;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        testSequenceId = UUID.randomUUID();
        pageable = PageRequest.of(0, 10);
        
        Sequence testSequence = new Sequence();
        testSequence.setId(testSequenceId);
        
        testQuestions = new ArrayList<>();
        
        Question testQuestion1 = new Question();
        testQuestion1.setId(UUID.randomUUID());
        testQuestion1.setTitle("Test Question 1");
        testQuestion1.setType("multiple_choice");
        testQuestion1.setPageNumber(1);
        testQuestion1.setSequence(testSequence);
        testQuestions.add(testQuestion1);
        
        Question testQuestion2 = new Question();
        testQuestion2.setId(UUID.randomUUID());
        testQuestion2.setTitle("Test Question 2");
        testQuestion2.setType("text");
        testQuestion2.setPageNumber(1);
        testQuestion2.setSequence(testSequence);
        testQuestions.add(testQuestion2);
    }

    @Test
    @DisplayName("Should find questions by sequence id successfully")
    void testFindQuestionsSuccess() {
        Page<Question> questionPage = new PageImpl<>(testQuestions, pageable, testQuestions.size());
        
        when(questionRepository.findBySequenceId(testSequenceId, pageable)).thenReturn(questionPage);

        PagedResponse<QuestionResource> result = questionService.findBySequenceId(testSequenceId, pageable);

        assertNotNull(result);
        assertNotNull(result.getContent());
        assertEquals(2, result.getContent().size());
        assertEquals("Test Question 1", result.getContent().get(0).getTitle());
        assertEquals("Test Question 2", result.getContent().get(1).getTitle());
        
        assertEquals(1, result.getPageInfo().getCurrentPage());
        assertEquals(10, result.getPageInfo().getSize());
        assertEquals(2, result.getPageInfo().getTotalItems());
        assertEquals(1, result.getPageInfo().getTotalPages());
        assertEquals(2, result.getPageInfo().getCountItemPerPage());
    }

    @Test
    @DisplayName("Should throw QuestionException when no questions found for sequence id")
    void testFindQuestionsEmpty() {
        UUID emptySequenceId = UUID.randomUUID();
        
        Page<Question> emptyPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
        
        when(questionRepository.findBySequenceId(emptySequenceId, pageable)).thenReturn(emptyPage);

        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.findBySequenceId(emptySequenceId, pageable);
        });

        assertEquals("No questions found for sequence id: " + emptySequenceId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
}