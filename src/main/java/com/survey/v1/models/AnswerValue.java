package com.survey.v1.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "answer_values")
public class AnswerValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    private Response response;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private QuestionOption option;
    
    @Column(name = "text_value", columnDefinition = "TEXT")
    private String textValue;
    
    @Column(name = "numeric_value", precision = 19, scale = 4)
    private BigDecimal numericValue;
    
    // Constructors
    public AnswerValue() {
        // Default constructor
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Response getResponse() {
        return response;
    }
    
    public void setResponse(Response response) {
        this.response = response;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public QuestionOption getOption() {
        return option;
    }
    
    public void setOption(QuestionOption option) {
        this.option = option;
    }
    
    public String getTextValue() {
        return textValue;
    }
    
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
    
    public BigDecimal getNumericValue() {
        return numericValue;
    }
    
    public void setNumericValue(BigDecimal numericValue) {
        this.numericValue = numericValue;
    }
}