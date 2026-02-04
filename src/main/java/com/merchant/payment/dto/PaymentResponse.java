package com.merchant.payment.dto;
import com.merchant.payment.domain.PaymentMethod;
import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.domain.PaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// dto/response/PaymentResponse.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private UUID id;
    private UUID merchantId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private Instant createdAt;

    public static PaymentResponse fromEntity(PaymentTransaction payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .merchantId(payment.getMerchant().getId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}