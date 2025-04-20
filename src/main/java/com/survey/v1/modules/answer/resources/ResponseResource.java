package com.survey.v1.modules.answer.resources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.survey.v1.models.Response;

import lombok.Data;

@Data
public class ResponseResource {
    private Long id;
    private UUID sequenceId;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private List<AnswerValueResource> answers;
    
    public static ResponseResource fromEntity(Response response) {
        ResponseResource resource = new ResponseResource();
        resource.setId(response.getId());
        resource.setSequenceId(response.getSequence() != null ? response.getSequence().getId() : null);
        resource.setIpAddress(response.getIpAddress());
        resource.setUserAgent(response.getUserAgent());
        resource.setCreatedAt(response.getCreatedAt());
        
        // Convert answer values
        if (response.getAnswerValues() != null && !response.getAnswerValues().isEmpty()) {
            resource.setAnswers(response.getAnswerValues().stream()
                    .map(AnswerValueResource::fromEntity)
                    .collect(Collectors.toList()));
        }
        
        return resource;
    }
}