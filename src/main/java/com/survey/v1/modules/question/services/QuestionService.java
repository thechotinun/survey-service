package com.survey.v1.modules.question.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.models.Question;
import com.survey.v1.models.response.PagedResponse;
import com.survey.v1.models.response.PagedResponse.PageInfo;
import com.survey.v1.modules.question.resources.QuestionResource;
import com.survey.v1.repositories.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public PagedResponse<QuestionResource> findBySequenceId(UUID sequenceId, Pageable pageable) {
        Page<Question> questions = questionRepository.findBySequenceId(sequenceId, pageable);

        if (questions.isEmpty()) {
            throw new QuestionException(
                    "No questions found for sequence id: " + sequenceId,
                    "No questions found for the specified survey sequence",
                    QuestionException.ErrorCode.QUESTION_NOT_FOUND);
        }

        List<QuestionResource> questionResources = questions.getContent().stream()
                .map(QuestionResource::fromEntity)
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(
            questions.getNumber() + 1,
            questions.getSize(),
            questions.getTotalElements(),
            questions.getTotalPages(),
            questions.getNumberOfElements());


        return new PagedResponse<>(questionResources, pageInfo);
    }
}
