package com.survey.v1.modules.question.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.repositories.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public List<Question> find(UUID sequenceId) {
        List<Question> questions = questionRepository.findBySequenceId(sequenceId);
        
        if (questions.isEmpty()) {
            throw new QuestionException(
                "No questions found for sequence id: " + sequenceId,
                "No questions found for the specified survey sequence",
                QuestionException.ErrorCode.QUESTION_NOT_FOUND
            );
        }
        
        return questions;
    }
}
