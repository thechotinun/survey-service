package com.survey.v1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "question_settings")
public class QuestionSetting extends Base {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    @Column(name = "setting_key")
    private String settingKey;
    
    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;
    
    // Constructors
    public QuestionSetting() {
        // Default constructor
    }
    
    // Getters and Setters
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public String getSettingKey() {
        return settingKey;
    }
    
    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }
    
    public String getSettingValue() {
        return settingValue;
    }
    
    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }
}