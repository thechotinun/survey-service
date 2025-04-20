package com.survey.v1.modules.answer.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ResponseCreateDTO {
    
    @NotNull(message = "IP address must not be null")
    private String ipAddress;
    
    private String userAgent;
    
    @NotEmpty(message = "Answers list must not be empty")
    @Valid
    private List<AnswerValueCreateDTO> answers; 
    
    @Data
    public static class AnswerValueDTO {
        
        @NotNull(message = "Question ID must not be null")
        private UUID questionId;
        
        private UUID optionId;
        
        private String textValue;
        
        private BigDecimal numericValue;
    }
}