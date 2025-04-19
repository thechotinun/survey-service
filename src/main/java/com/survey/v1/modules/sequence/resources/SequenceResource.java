package com.survey.v1.modules.sequence.resources;

import com.survey.v1.common.enums.Status;
import java.time.LocalDateTime;
import java.util.UUID;

import com.survey.v1.models.Sequence;

import lombok.Data;

@Data
public class SequenceResource {
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime createdAt;

    public static SequenceResource fromSequence(Sequence sequence) {
        SequenceResource sequenceResource = new SequenceResource();
        sequenceResource.setId(sequence.getId());
        sequenceResource.setTitle(sequence.getTitle());
        sequenceResource.setDescription(sequence.getDescription());
        sequenceResource.setStatus(sequence.getStatus());
        sequenceResource.setCreatedAt(sequence.getCreatedAt());

        return sequenceResource;
    }
}
