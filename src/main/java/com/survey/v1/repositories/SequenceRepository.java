package com.survey.v1.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survey.v1.models.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, UUID>{
    
}
