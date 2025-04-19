package com.survey.v1.modules.sequence.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.v1.exceptions.SequenceException;
import com.survey.v1.models.Sequence;
import com.survey.v1.repositories.SequenceRepository;

@Service
public class SequenceService {
    @Autowired
    private SequenceRepository sequenceRepository;

    public Sequence find(UUID id) {
        return sequenceRepository.findById(id).orElseThrow(
                () -> new SequenceException("Sequence not found with id: " + id,
                        "The requested sequence could not be found",
                        SequenceException.ErrorCode.SEQUENCE_NOT_FOUND));
    }
}
