package com.merchant.payment.domain;

// domain/model/PaymentStatus.java
public enum PaymentStatus {
    INIT,
    AUTHORIZED,
    SETTLED,
    FAILED
}