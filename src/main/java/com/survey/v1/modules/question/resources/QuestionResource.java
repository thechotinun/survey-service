package com.survey.v1.modules.question.resources;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.survey.v1.models.Question;

import lombok.Data;

@Data
public class QuestionResource {
    private UUID id;
    private UUID sequenceId;
    private String title;
    private String type;
    private Integer pageNumber;
    private LocalDateTime createdAt;
    private Set<QuestionOptionResource> options;
    private Set<QuestionSettingResource> settings;
    
    public static QuestionResource fromEntity(Question question) {
        QuestionResource resource = new QuestionResource();
        resource.setId(question.getId());
        resource.setSequenceId(question.getSequence() != null ? question.getSequence().getId() : null);
        resource.setTitle(question.getTitle());
        resource.setType(question.getType());
        resource.setPageNumber(question.getPageNumber());
        resource.setCreatedAt(question.getCreatedAt());
        
        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
            resource.setOptions(question.getOptions().stream()
                    .map(QuestionOptionResource::fromEntity)
                    .collect(Collectors.toSet()));
        }
        
        if (question.getSettings() != null && !question.getSettings().isEmpty()) {
            resource.setSettings(question.getSettings().stream()
                    .map(QuestionSettingResource::fromEntity)
                    .collect(Collectors.toSet()));
        }
        
        return resource;
    }
}