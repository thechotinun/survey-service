package com.survey.v1.repositories;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survey.v1.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    
    Page<Question> findBySequenceId(UUID sequenceId, Pageable pageable);
    
}
