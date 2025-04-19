package com.survey.v1.modules.question.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.survey.v1.models.response.PagedResponse;
import com.survey.v1.models.response.SuccessResponse;
import com.survey.v1.modules.question.resources.QuestionResource;
import com.survey.v1.modules.question.services.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/questions")
@Tag(name = "Questions", description = "Sequences management APIs")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Find sequence by uuid", description = "Returns a single sequence by its uuid")
    @GetMapping("/{seq_id}")
    public ResponseEntity<SuccessResponse<PagedResponse<QuestionResource>>> find(
            @PathVariable UUID seq_id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        PagedResponse<QuestionResource> pagedResponse = questionService.findBySequenceId(seq_id, pageable);

        SuccessResponse<PagedResponse<QuestionResource>> response = new SuccessResponse<>(
                pagedResponse,
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase());

        return ResponseEntity.ok(response);
    }
}
