package com.survey.v1.modules.sequence.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.survey.v1.exceptions.SequenceException;
import com.survey.v1.models.Sequence;
import com.survey.v1.repositories.SequenceRepository;

@ExtendWith(MockitoExtension.class)
public class SequenceServiceTest {

    @Mock
    private SequenceRepository sequenceRepository;

    @InjectMocks
    private SequenceService sequenceService;

    private UUID testId;
    private Sequence testSequence;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        
        testSequence = new Sequence();
        testSequence.setId(testId);
    }

    @Test
    @DisplayName("Should find sequence by id successfully")
    void testFindSequenceSuccess() {
        when(sequenceRepository.findById(testId)).thenReturn(Optional.of(testSequence));

        Sequence result = sequenceService.find(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
    }

    @Test
    @DisplayName("Should throw SequenceException when sequence not found")
    void testFindSequenceNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        
        when(sequenceRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        SequenceException exception = assertThrows(SequenceException.class, () -> {
            sequenceService.find(nonExistentId);
        });

        assertEquals("Sequence not found with id: " + nonExistentId, exception.getMessage());
        assertEquals("The requested sequence could not be found", exception.getDetailMessage());
        assertEquals(SequenceException.ErrorCode.SEQUENCE_NOT_FOUND, exception.getErrorCode());
    }
}