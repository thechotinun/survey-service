package com.survey.v1.modules.question.resources;

import java.time.LocalDateTime;
import java.util.UUID;

import com.survey.v1.models.QuestionOption;

import lombok.Data;

@Data
public class QuestionOptionResource {
    private UUID id;
    private UUID questionId;
    private String optionText;
    private String optionValue;
    private Integer orderNum;
    private LocalDateTime createdAt;
    
    public static QuestionOptionResource fromEntity(QuestionOption option) {
        QuestionOptionResource resource = new QuestionOptionResource();
        resource.setId(option.getId());
        resource.setQuestionId(option.getQuestion() != null ? option.getQuestion().getId() : null);
        resource.setOptionText(option.getOptionText());
        resource.setOptionValue(option.getOptionValue());
        resource.setOrderNum(option.getOrderNum());
        resource.setCreatedAt(option.getCreatedAt());
        return resource;
    }
}