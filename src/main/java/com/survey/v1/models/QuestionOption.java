package com.survey.v1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "question_options")
public class QuestionOption extends Base {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    @Column(name = "option_text", columnDefinition = "TEXT")
    private String optionText;
    
    @Column(name = "option_value")
    private String optionValue;
    
    @Column(name = "order_num")
    private Integer orderNum;
    
    // Constructors
    public QuestionOption() {
        // Default constructor
    }
    
    // Getters and Setters
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public String getOptionText() {
        return optionText;
    }
    
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
    
    public String getOptionValue() {
        return optionValue;
    }
    
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
    
    public Integer getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}