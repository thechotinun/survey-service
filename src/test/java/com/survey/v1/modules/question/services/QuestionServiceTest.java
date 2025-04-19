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

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.models.Sequence;
import com.survey.v1.repositories.QuestionRepository;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private UUID testSequenceId;
    private List<Question> testQuestions;

    @BeforeEach
    void setUp() {
        testSequenceId = UUID.randomUUID();
        
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
        when(questionRepository.findBySequenceId(testSequenceId)).thenReturn(testQuestions);

        List<Question> results = questionService.find(testSequenceId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Test Question 1", results.get(0).getTitle());
        assertEquals("Test Question 2", results.get(1).getTitle());
    }

    @Test
    @DisplayName("Should throw QuestionException when no questions found for sequence id")
    void testFindQuestionsEmpty() {
        UUID emptySequenceId = UUID.randomUUID();
        
        when(questionRepository.findBySequenceId(emptySequenceId)).thenReturn(new ArrayList<>());

        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.find(emptySequenceId);
        });

        assertEquals("No questions found for sequence id: " + emptySequenceId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
}