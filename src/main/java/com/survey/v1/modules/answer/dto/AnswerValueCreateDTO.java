package com.survey.v1.modules.answer.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnswerValueCreateDTO {
    
    @NotNull(message = "Question ID must not be null")
    private UUID questionId;
    
    private UUID optionId;
    
    private String textValue;
    
    private BigDecimal numericValue;
}