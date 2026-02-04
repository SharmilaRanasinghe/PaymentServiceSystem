// src/main/java/com/merchant/payment/dto/response/ApiResponse.java
package com.merchant.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {
    
    @Schema(description = "Indicates if the request was successful", example = "true")
    private boolean success;
    
    @Schema(description = "Response message", example = "Merchant created successfully")
    private String message;
    
    @Schema(description = "Response data payload")
    private T data;
    
    @Schema(description = "Timestamp of the response", example = "2024-01-20T10:30:00.000Z")
    private Instant timestamp;
    
    @Schema(description = "Error code if request failed", example = "VALIDATION_ERROR")
    private String errorCode;
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .timestamp(Instant.now())
            .build();
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operation completed successfully");
    }
    
    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return ApiResponse.<T>builder()
            .success(false)
            .errorCode(errorCode)
            .message(message)
            .timestamp(Instant.now())
            .build();
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return error("INTERNAL_ERROR", message);
    }
}

