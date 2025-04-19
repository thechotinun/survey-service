package com.survey.v1.modules.sequence.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.v1.models.Sequence;
import com.survey.v1.models.response.SuccessResponse;
import com.survey.v1.modules.sequence.resources.SequenceResource;
import com.survey.v1.modules.sequence.services.SequenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/sequences")
@Tag(name = "Sequences", description = "Sequences management APIs")
public class SequenceController {
    @Autowired
    private SequenceService sequenceService;

    @Operation(
        summary = "Find sequence by uuid",
        description = "Returns a single sequence by its uuid"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sequence found successfully"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Sequence not found"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<SequenceResource>> find(@PathVariable UUID id) {
        Sequence sequence = sequenceService.find(id);
        SequenceResource sequenceResource = SequenceResource.fromSequence(sequence);

        SuccessResponse<SequenceResource> response = new SuccessResponse<>(sequenceResource, HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.ok(response);
    }

}
