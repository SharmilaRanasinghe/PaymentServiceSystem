package com.merchant.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

// Statistics helper class
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MerchantStatistics {
    private Integer totalTransactions;
    private Double totalTransactionValue;
    private Instant lastTransactionDate;
    private Double averageTransactionValue;
}