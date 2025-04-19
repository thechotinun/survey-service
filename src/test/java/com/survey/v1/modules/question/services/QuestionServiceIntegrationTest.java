package com.survey.v1.modules.question.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.models.Sequence;
import com.survey.v1.models.response.PagedResponse;
import com.survey.v1.modules.question.resources.QuestionResource;
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
    private Pageable pageable;
    
    @BeforeEach
    void setUp() {
        logger.info("Setting up test data...");
        pageable = PageRequest.of(0, 10);
        
        testSequence = new Sequence();
        testSequence = sequenceRepository.save(testSequence);
        testSequenceId = testSequence.getId();
        logger.info("Created test sequence with ID: " + testSequenceId);
        
        Sequence emptySequence = new Sequence();
        emptySequence = sequenceRepository.save(emptySequence);
        emptySequenceId = emptySequence.getId();
        logger.info("Created empty sequence with ID: " + emptySequenceId);
        
        for (int i = 1; i <= 3; i++) {
            Question question = new Question();
            question.setTitle("Test Question " + i);
            question.setType(i % 2 == 0 ? "text" : "multiple_choice");
            question.setPageNumber(1);
            question.setSequence(testSequence);
            questionRepository.save(question);
            logger.info("Created question: Title='" + question.getTitle() + "', Type='" + question.getType() + "'");
        }
        
        logger.info("Total questions in database: " + questionRepository.count());
    }
    
    @Test
    @DisplayName("Should find questions by sequence id successfully")
    void testFindQuestionsSuccess() {
        logger.info("Testing find questions with pagination for sequence ID: " + testSequenceId);
        
        PagedResponse<QuestionResource> result = questionService.findBySequenceId(testSequenceId, pageable);
        
        logger.info("Found " + result.getContent().size() + " questions (Page " + 
                   result.getPageInfo().getCurrentPage() + " of " + 
                   result.getPageInfo().getTotalPages() + ")");
        
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertEquals(3, result.getContent().size());
        
        for (int i = 0; i < result.getContent().size(); i++) {
            assertEquals("Test Question " + (i + 1), result.getContent().get(i).getTitle());
        }
        
        assertEquals(1, result.getPageInfo().getCurrentPage());
        assertEquals(10, result.getPageInfo().getSize());
        assertEquals(3, result.getPageInfo().getTotalItems());
        assertEquals(1, result.getPageInfo().getTotalPages());
        assertEquals(3, result.getPageInfo().getCountItemPerPage());
    }
    
    @Test
    @DisplayName("Should throw QuestionException when no questions found for sequence id")
    void testFindQuestionsEmpty() {
        logger.info("Testing find questions with empty result for sequence ID: " + emptySequenceId);
        
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.findBySequenceId(emptySequenceId, pageable);
        });
        
        logger.info("Exception thrown as expected: " + exception.getMessage());
        
        assertEquals("No questions found for sequence id: " + emptySequenceId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
    
    @Test
    @DisplayName("Should throw QuestionException when sequence id does not exist")
    void testFindQuestionsWithInvalidSequenceId() {
        UUID nonExistentId = UUID.randomUUID();
        logger.info("Testing find questions with non-existent sequence ID: " + nonExistentId);
        
        QuestionException exception = assertThrows(QuestionException.class, () -> {
            questionService.findBySequenceId(nonExistentId, pageable);
        });
        
        logger.info("Exception thrown as expected: " + exception.getMessage());
        
        assertEquals("No questions found for sequence id: " + nonExistentId, exception.getMessage());
        assertEquals("No questions found for the specified survey sequence", exception.getDetailMessage());
        assertEquals(QuestionException.ErrorCode.QUESTION_NOT_FOUND, exception.getErrorCode());
    }
}