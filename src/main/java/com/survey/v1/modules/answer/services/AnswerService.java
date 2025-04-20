package com.survey.v1.modules.answer.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.v1.models.AnswerValue;
import com.survey.v1.models.Question;
import com.survey.v1.models.QuestionOption;
import com.survey.v1.models.Response;
import com.survey.v1.models.Sequence;
import com.survey.v1.modules.answer.dto.ResponseCreateDTO;
import com.survey.v1.modules.answer.dto.AnswerValueCreateDTO;
import com.survey.v1.repositories.AnswerValueRepository;
import com.survey.v1.repositories.QuestionOptionRepository;
import com.survey.v1.repositories.QuestionRepository;
import com.survey.v1.repositories.ResponseRepository;
import com.survey.v1.repositories.SequenceRepository;

@Service
public class AnswerService {
    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    AnswerValueRepository answerValueRepository;

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionOptionRepository questionOptionRepository;

    public Response saveResponse(String sequenceId, ResponseCreateDTO responseCreateDTO) {
        Sequence sequence = sequenceRepository.findById(UUID.fromString(sequenceId))
                .orElseThrow(() -> new RuntimeException("Sequence not found"));

        Response response = new Response();
        response.setSequence(sequence);
        response.setIpAddress(responseCreateDTO.getIpAddress());
        response.setUserAgent(responseCreateDTO.getUserAgent());

        for (AnswerValueCreateDTO answerValueCreateDTO : responseCreateDTO.getAnswers()) {
            Question question = questionRepository.findById(answerValueCreateDTO.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            AnswerValue answer = new AnswerValue();
            answer.setQuestion(question);

            if (answerValueCreateDTO.getOptionId() != null) {
                QuestionOption option = questionOptionRepository.findById(answerValueCreateDTO.getOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found"));
                answer.setOption(option);
            }
            if (answerValueCreateDTO.getTextValue() != null) {
                answer.setTextValue(answerValueCreateDTO.getTextValue());
            }
            if (answerValueCreateDTO.getNumericValue() != null) {
                answer.setNumericValue(answerValueCreateDTO.getNumericValue());
            }

            response.addAnswerValue(answer);
        }

        return responseRepository.save(response);
    }
}
