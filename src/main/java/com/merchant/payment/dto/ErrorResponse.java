// src/main/java/com/merchant/payment/dto/response/ErrorResponse.java
package com.merchant.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response details")
public class ErrorResponse {
    
    @Schema(description = "Error code", example = "VALIDATION_ERROR")
    private String errorCode;
    
    @Schema(description = "Error message", example = "Email already exists")
    private String message;
    
    @Schema(description = "Timestamp when error occurred", example = "2024-01-20T10:30:00.000Z")
    private Instant timestamp;
    
    @Schema(description = "Detailed error information")
    private List<FieldError> fieldErrors;
    
    @Schema(description = "Path where error occurred", example = "/api/v1/merchants")
    private String path;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        @Schema(description = "Field name", example = "email")
        private String field;
        
        @Schema(description = "Error message", example = "Email must be valid")
        private String message;
        
        @Schema(description = "Rejected value", example = "invalid-email")
        private Object rejectedValue;
    }
    
    public static ErrorResponse of(String errorCode, String message, String path) {
        return ErrorResponse.builder()
            .errorCode(errorCode)
            .message(message)
            .timestamp(Instant.now())
            .path(path)
            .build();
    }
}