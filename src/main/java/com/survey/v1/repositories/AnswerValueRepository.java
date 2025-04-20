package com.survey.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survey.v1.models.AnswerValue;

@Repository
public interface AnswerValueRepository extends JpaRepository<AnswerValue, Long>{
    
}
