package com.survey.v1.modules.answer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.v1.models.Response;
import com.survey.v1.models.response.SuccessResponse;
import com.survey.v1.modules.answer.dto.ResponseCreateDTO;
import com.survey.v1.modules.answer.resources.ResponseResource;
import com.survey.v1.modules.answer.services.AnswerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/survay-answer")
@Tag(name = "Answer", description = "Answer management APIs")
public class AnswerController {
    
    @Autowired
    AnswerService answerService;

    @PostMapping("/{seq_id}")
    public ResponseEntity<SuccessResponse<ResponseResource>> createSurveyAnswer(
            @PathVariable String seq_id,
            @Valid @RequestBody ResponseCreateDTO responseCreateDTO) {
        
        Response savedResponse = answerService.saveResponse(seq_id, responseCreateDTO);
        
        ResponseResource responseResource = ResponseResource.fromEntity(savedResponse);
        
        SuccessResponse<ResponseResource> response = new SuccessResponse<>(
                responseResource,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
