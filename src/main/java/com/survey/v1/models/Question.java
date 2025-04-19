package com.survey.v1.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question extends Base {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_id")
    private Sequence sequence;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "page_number")
    private Integer pageNumber;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionOption> options = new HashSet<>();
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionSetting> settings = new HashSet<>();
    
    // Constructors
    public Question() {
        // Default constructor
    }
    
    // Getters and Setters
    public Sequence getSequence() {
        return sequence;
    }
    
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public Set<QuestionOption> getOptions() {
        return options;
    }
    
    public void setOptions(Set<QuestionOption> options) {
        this.options = options;
    }
    
    // Helper method to add option
    public void addOption(QuestionOption option) {
        options.add(option);
        option.setQuestion(this);
    }
    
    // Helper method to remove option
    public void removeOption(QuestionOption option) {
        options.remove(option);
        option.setQuestion(null);
    }
    
    public Set<QuestionSetting> getSettings() {
        return settings;
    }
    
    public void setSettings(Set<QuestionSetting> settings) {
        this.settings = settings;
    }
    
    // Helper method to add setting
    public void addSetting(QuestionSetting setting) {
        settings.add(setting);
        setting.setQuestion(this);
    }
    
    // Helper method to remove setting
    public void removeSetting(QuestionSetting setting) {
        settings.remove(setting);
        setting.setQuestion(null);
    }
}