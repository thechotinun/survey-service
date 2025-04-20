package com.survey.v1.modules.answer.resources;

import java.math.BigDecimal;
import java.util.UUID;

import com.survey.v1.models.AnswerValue;

import lombok.Data;

@Data
public class AnswerValueResource {
    private Long id;
    private UUID questionId;
    private String questionTitle;
    private String questionType;
    private UUID optionId;
    private String optionText;
    private String textValue;
    private BigDecimal numericValue;
    
    public static AnswerValueResource fromEntity(AnswerValue answerValue) {
        AnswerValueResource resource = new AnswerValueResource();
        resource.setId(answerValue.getId());
        
        // Set question information
        if (answerValue.getQuestion() != null) {
            resource.setQuestionId(answerValue.getQuestion().getId());
            resource.setQuestionTitle(answerValue.getQuestion().getTitle());
            resource.setQuestionType(answerValue.getQuestion().getType());
        }
        
        // Set option information
        if (answerValue.getOption() != null) {
            resource.setOptionId(answerValue.getOption().getId());
            resource.setOptionText(answerValue.getOption().getOptionText());
        }
        
        // Set values
        resource.setTextValue(answerValue.getTextValue());
        resource.setNumericValue(answerValue.getNumericValue());
        
        return resource;
    }
}