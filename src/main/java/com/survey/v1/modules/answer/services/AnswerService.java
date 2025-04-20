package com.survey.v1.modules.answer.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.v1.exceptions.QuestionException;
import com.survey.v1.exceptions.SequenceException;
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
                .orElseThrow(() -> new SequenceException("Sequence not found with id: " + sequenceId,
                        "The requested sequence could not be found",
                        SequenceException.ErrorCode.SEQUENCE_NOT_FOUND));

        Response response = new Response();
        response.setSequence(sequence);
        response.setIpAddress(responseCreateDTO.getIpAddress());
        response.setUserAgent(responseCreateDTO.getUserAgent());

        for (AnswerValueCreateDTO answerValueCreateDTO : responseCreateDTO.getAnswers()) {
            Question question = questionRepository.findById(answerValueCreateDTO.getQuestionId())
                    .orElseThrow(() -> new QuestionException(
                            "No questions found for question id: " + answerValueCreateDTO.getQuestionId(),
                            "No questions found for the specified questions",
                            QuestionException.ErrorCode.QUESTION_NOT_FOUND));

            AnswerValue answer = new AnswerValue();
            answer.setQuestion(question);

            if (answerValueCreateDTO.getOptionId() != null) {
                QuestionOption option = questionOptionRepository.findById(answerValueCreateDTO.getOptionId())
                        .orElseThrow(() -> new QuestionException(
                                "No options found for option id: " + answerValueCreateDTO.getQuestionId(),
                                "No options found for the specified options",
                                QuestionException.ErrorCode.QUESTION_OPTION_NOT_FOUND));
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
