package com.survey.v1.modules.question.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.models.Sequence;
import com.survey.v1.repositories.QuestionRepository;
import com.survey.v1.repositories.SequenceRepository;
import com.survey.v1.utils.LoggerUtils;

@DataJpaTest
@Import(QuestionService.class)
public class QuestionServiceIntegrationTest {

    private final LoggerUtils logger = new LoggerUtils(QuestionServiceIntegrationTest.class);

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private SequenceRepository sequenceRepository;
    
    @Autowired
    private QuestionService questionService;
    
    private Sequence testSequence;
    private UUID testSequenceId;
    private UUID emptySequenceId;
    
    @BeforeEach
    void setUp() {
        logger.info("Setting up test data...");
        
        testSequence = new Sequence();
        testSequence = sequenceRepository.save(testSequence);
        testSequenceId = testSequence.getId();
        
        Sequence emptySequence = new Sequence();
        
        emptySequence = sequenceRepository.save(emptySequence);
        emptySequenceId = emptySequence.getId();
        
        for (int i = 1; i <= 3; i++) {
            Question question = new Question();
            question.setTitle("Test Question " + i);
            question.setType(i % 2 == 0 ? "text" : "multiple_choice");
            question.setPageNumber(1);
            question.setSequence(testSequence);
            questionRepository.save(question);
        }
        
        logger.info("Total questions in database: " + questionRepository.count());
    }
    
    @Test
    @DisplayName("Should find questions by sequence id successfully")
    void testFindQuestionsSuccess() {
        List<Question> results = questionService.find(testSequenceId);
        
        assertNotNull(results);
        assertEquals(3, results.size());
        
        for (int i = 0; i < results.size(); i++) {
            assertEquals("Test Question " + (i + 1), results.get(i).getTitle());
            assertEquals(testSequenceId, results.get(i).getSequence().getId());
        }
    }
    
    @Test
    @DisplayName("Should throw QuestionException when no questions found for sequence id")
    void testFindQuestionsEmpty() {
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.find(emptySequenceId);
        });
        
        assertEquals("No questions found for sequence id: " + emptySequenceId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
    
    @Test
    @DisplayName("Should throw QuestionException when sequence id does not exist")
    void testFindQuestionsWithInvalidSequenceId() {
        UUID nonExistentId = UUID.randomUUID();
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.find(nonExistentId);
        });
        
        assertEquals("No questions found for sequence id: " + nonExistentId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
}