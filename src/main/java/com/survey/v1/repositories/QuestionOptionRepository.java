package com.survey.v1.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.survey.v1.models.QuestionOption;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, UUID> {
    
}
