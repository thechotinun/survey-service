package com.survey.v1.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "responses")
public class Response {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seq_id")
    private Sequence sequence;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnswerValue> answerValues = new HashSet<>();
    
    // Constructors
    public Response() {
        // Default constructor
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Sequence getSequence() {
        return sequence;
    }
    
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Set<AnswerValue> getAnswerValues() {
        return answerValues;
    }
    
    public void setAnswerValues(Set<AnswerValue> answerValues) {
        this.answerValues = answerValues;
    }
    
    // Helper methods for managing the bidirectional relationship
    public void addAnswerValue(AnswerValue answerValue) {
        answerValues.add(answerValue);
        answerValue.setResponse(this);
    }
    
    public void removeAnswerValue(AnswerValue answerValue) {
        answerValues.remove(answerValue);
        answerValue.setResponse(null);
    }
    
    // PrePersist method to set createdAt if not already set
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}