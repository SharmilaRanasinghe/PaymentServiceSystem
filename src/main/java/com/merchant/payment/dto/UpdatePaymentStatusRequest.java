// src/main/java/com/merchant/payment/dto/request/UpdatePaymentStatusRequest.java
package com.merchant.payment.dto;

import com.merchant.payment.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for updating payment status")
public class UpdatePaymentStatusRequest {
    
    @NotNull(message = "Status is required")
    @Schema(
        description = "New payment status",
        example = "AUTHORIZED",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private PaymentStatus status;
    
    @Schema(
        description = "Reason for status update (especially for FAILED status)",
        example = "Insufficient funds",
        maxLength = 500
    )
    private String reason;
    
    @Schema(
        description = "Reference from payment gateway or processor",
        example = "gateway_ref_123456",
        maxLength = 100
    )
    private String gatewayReference;
    
    @Schema(
        description = "Authorization code from payment processor",
        example = "AUTH12345",
        maxLength = 50
    )
    private String authorizationCode;
    
    @Schema(
        description = "Additional notes about the status update",
        example = "Payment manually authorized by admin",
        maxLength = 1000
    )
    private String notes;
}