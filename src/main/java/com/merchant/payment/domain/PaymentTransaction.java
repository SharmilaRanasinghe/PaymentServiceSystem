package com.merchant.payment.domain;

import com.merchant.payment.exception.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// domain/model/PaymentTransaction.java
@Entity
@Table(name = "payment_transactions")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    
    @NotNull
    @Positive
    @Column(precision = 19, scale = 4)
    private BigDecimal amount;
    
    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.INIT;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    
    @Version
    private Long version;
    
    // Business logic for status transitions
    public void updateStatus(PaymentStatus newStatus) {
        validateStatusTransition(newStatus);
        this.status = newStatus;
    }
    
    private void validateStatusTransition(PaymentStatus newStatus) {
        Map<PaymentStatus, Set<PaymentStatus>> allowedTransitions = Map.of(
            PaymentStatus.INIT, Set.of(PaymentStatus.AUTHORIZED, PaymentStatus.FAILED),
            PaymentStatus.AUTHORIZED, Set.of(PaymentStatus.SETTLED, PaymentStatus.FAILED),
            PaymentStatus.SETTLED, Set.of(),
            PaymentStatus.FAILED, Set.of()
        );
        
        if (!allowedTransitions.getOrDefault(this.status, Set.of()).contains(newStatus)) {
            throw new BusinessException(
                "INVALID_STATUS_TRANSITION",
                String.format("Cannot transition from %s to %s", this.status, newStatus)
            );
        }
    }
}