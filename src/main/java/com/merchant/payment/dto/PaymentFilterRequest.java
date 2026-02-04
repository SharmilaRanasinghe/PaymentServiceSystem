// src/main/java/com/merchant/payment/dto/request/PaymentFilterRequest.java
package com.merchant.payment.dto;

import com.merchant.payment.domain.PaymentMethod;
import com.merchant.payment.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Filters for payment listing")
public class PaymentFilterRequest {
    
    @Schema(description = "Merchant ID filter", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID merchantId;
    
    @Schema(description = "Payment status filter", example = "SETTLED")
    private PaymentStatus status;
    
    @Schema(description = "Payment method filter", example = "CARD")
    private PaymentMethod paymentMethod;
    
    @Schema(description = "Currency filter", example = "LKR")
    private String currency;
    
    @Schema(description = "Minimum amount filter", example = "100.00")
    private BigDecimal minAmount;
    
    @Schema(description = "Maximum amount filter", example = "10000.00")
    private BigDecimal maxAmount;
    
    @Schema(description = "Start date filter (inclusive)", example = "2024-01-01")
    private LocalDate fromDate;
    
    @Schema(description = "End date filter (inclusive)", example = "2024-01-31")
    private LocalDate toDate;
    
    @Schema(description = "Search in description or reference", example = "Order #123")
    private String search;
    
    @Schema(description = "Customer email filter", example = "customer@example.com")
    private String customerEmail;
    
    @Schema(description = "Order ID filter", example = "ORD-2024-001")
    private String orderId;
    
    @Schema(description = "Page number (0-indexed)", example = "0", defaultValue = "0")
    @Builder.Default
    private Integer page = 0;
    
    @Schema(description = "Page size", example = "10", defaultValue = "10")
    @Builder.Default
    private Integer size = 10;
    
    @Schema(
        description = "Sort field and direction",
        example = "createdAt,desc",
        allowableValues = {"createdAt,asc", "createdAt,desc", "amount,asc", "amount,desc"},
        defaultValue = "createdAt,desc"
    )
    @Builder.Default
    private String sort = "createdAt,desc";
}