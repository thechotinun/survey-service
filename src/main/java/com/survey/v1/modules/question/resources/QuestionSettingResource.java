package com.survey.v1.modules.question.resources;

import java.time.LocalDateTime;
import java.util.UUID;

import com.survey.v1.models.QuestionSetting;

import lombok.Data;

@Data
public class QuestionSettingResource {
    private UUID id;
    private UUID questionId;
    private String settingKey;
    private String settingValue;
    private LocalDateTime createdAt;
    
    public static QuestionSettingResource fromEntity(QuestionSetting setting) {
        QuestionSettingResource resource = new QuestionSettingResource();
        resource.setId(setting.getId());
        resource.setQuestionId(setting.getQuestion() != null ? setting.getQuestion().getId() : null);
        resource.setSettingKey(setting.getSettingKey());
        resource.setSettingValue(setting.getSettingValue());
        resource.setCreatedAt(setting.getCreatedAt());
        return resource;
    }
}