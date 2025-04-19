package com.survey.v1.modules.sequence.services;

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

import com.survey.v1.exceptions.SequenceException;
import com.survey.v1.models.Sequence;
import com.survey.v1.repositories.SequenceRepository;

@DataJpaTest
@Import(SequenceService.class)
public class SequenceServiceIntegrationTest {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private SequenceService sequenceService;

    private UUID testId;
    private Sequence testSequence;

    @BeforeEach
    void setUp() {
        testSequence = new Sequence();
        
        testSequence = sequenceRepository.save(testSequence);
        testId = testSequence.getId();
    }

    @Test
    @DisplayName("Should find sequence by id successfully")
    void testFindSequenceSuccess() {
        Sequence result = sequenceService.find(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
    }

    @Test
    @DisplayName("Should throw SequenceException when sequence not found")
    void testFindSequenceNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        
        SequenceException exception = assertThrows(SequenceException.class, () -> {
            sequenceService.find(nonExistentId);
        });

        assertEquals("Sequence not found with id: " + nonExistentId, exception.getMessage());
        assertEquals("The requested sequence could not be found", exception.getDetailMessage());
        assertEquals(SequenceException.ErrorCode.SEQUENCE_NOT_FOUND, exception.getErrorCode());
    }
}