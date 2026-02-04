// src/main/java/com/merchant/payment/dto/response/MerchantResponse.java
package com.merchant.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merchant.payment.domain.Merchant;
import com.merchant.payment.domain.MerchantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Merchant information response")
public class MerchantResponse {
    
    @Schema(description = "Unique identifier of the merchant", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "Business name of the merchant", example = "ABC Stores")
    private String name;
    
    @Schema(description = "Contact email address", example = "contact@abc.com")
    private String email;
    
    @Schema(description = "Current status of the merchant", example = "ACTIVE")
    private MerchantStatus status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Schema(description = "When the merchant was created", example = "2024-01-20T10:30:00.000Z")
    private Instant createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Schema(description = "When the merchant was last updated", example = "2024-01-20T10:30:00.000Z")
    private Instant updatedAt;
    
    // Optional fields for detailed responses
    @Schema(description = "Total number of transactions", example = "150")
    private Integer totalTransactions;
    
    @Schema(description = "Total value of all transactions", example = "250000.50")
    private Double totalTransactionValue;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Schema(description = "Date of last transaction", example = "2024-01-20T10:30:00.000Z")
    private Instant lastTransactionDate;
    
    @Schema(description = "Average transaction value", example = "1666.67")
    private Double averageTransactionValue;
    
    @Schema(description = "Business contact phone number", example = "+94112233445")
    private String phone;
    
    @Schema(description = "Business address", example = "123 Main Street, Colombo")
    private String address;
    
    /**
     * Basic conversion from Merchant entity to MerchantResponse
     */
    public static MerchantResponse fromEntity(Merchant merchant) {
        return MerchantResponse.builder()
            .id(merchant.getId())
            .name(merchant.getName())
            .email(merchant.getEmail())
            .status(merchant.getStatus())
            .createdAt(merchant.getCreatedAt())
            .build();
    }
    
    /**
     * Conversion with business statistics
     */
    public static MerchantResponse fromEntityWithStats(Merchant merchant, 
                                                      MerchantStatistics statistics) {
        return MerchantResponse.builder()
            .id(merchant.getId())
            .name(merchant.getName())
            .email(merchant.getEmail())
            .status(merchant.getStatus())
            .createdAt(merchant.getCreatedAt())
            .totalTransactions(statistics.getTotalTransactions())
            .totalTransactionValue(statistics.getTotalTransactionValue())
            .lastTransactionDate(statistics.getLastTransactionDate())
            .averageTransactionValue(statistics.getAverageTransactionValue())
            .build();
    }
    
    /**
     * Creates a summary response (for list views)
     */
    public static MerchantResponse summary(Merchant merchant) {
        return MerchantResponse.builder()
            .id(merchant.getId())
            .name(merchant.getName())
            .email(merchant.getEmail())
            .status(merchant.getStatus())
            .createdAt(merchant.getCreatedAt())
            .build();
    }
    
    /**
     * Creates a minimal response (for reference only)
     */
    public static MerchantResponse minimal(Merchant merchant) {
        return MerchantResponse.builder()
            .id(merchant.getId())
            .name(merchant.getName())
            .status(merchant.getStatus())
            .build();
    }
}
