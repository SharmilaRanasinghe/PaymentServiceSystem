// src/main/java/com/merchant/payment/domain/event/PaymentStatusChangedEvent.java
package com.merchant.payment.domain;

import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.domain.PaymentTransaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class PaymentStatusChangedEvent extends ApplicationEvent {
    
    private final UUID paymentId;
    private final UUID merchantId;
    private final PaymentStatus oldStatus;
    private final PaymentStatus newStatus;
    private final BigDecimal amount;
    private final String currency;
    private final Instant timestamp;
    private final String eventType = "PAYMENT_STATUS_CHANGED";
    
    public PaymentStatusChangedEvent(Object source, 
                                    PaymentTransaction payment, 
                                    PaymentStatus newStatus) {
        super(source);
        this.paymentId = payment.getId();
        this.merchantId = payment.getMerchant().getId();
        this.oldStatus = payment.getStatus();
        this.newStatus = newStatus;
        this.amount = payment.getAmount();
        this.currency = payment.getCurrency();
        this.timestamp = Instant.now();
    }
    
    public PaymentStatusChangedEvent(Object source,
                                    UUID paymentId,
                                    UUID merchantId,
                                    PaymentStatus oldStatus,
                                    PaymentStatus newStatus,
                                    BigDecimal amount,
                                    String currency) {
        super(source);
        this.paymentId = paymentId;
        this.merchantId = merchantId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = Instant.now();
    }
    
    public boolean isSettled() {
        return newStatus == PaymentStatus.SETTLED;
    }
    
    public boolean isFailed() {
        return newStatus == PaymentStatus.FAILED;
    }
    
    public boolean isAuthorized() {
        return newStatus == PaymentStatus.AUTHORIZED;
    }
    
    @Override
    public String toString() {
        return String.format(
            "PaymentStatusChangedEvent{paymentId=%s, merchantId=%s, oldStatus=%s, newStatus=%s, amount=%s %s}",
            paymentId, merchantId, oldStatus, newStatus, amount, currency
        );
    }
}