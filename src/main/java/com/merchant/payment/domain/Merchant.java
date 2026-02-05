package com.merchant.payment.domain;

import com.merchant.payment.exception.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;
// domain/model/Merchant.java
@Entity
@Table(name = "merchants")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    private UUID id;
    
    @NotBlank
    @Size(max = 255)
    private String name;
    
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status = MerchantStatus.PENDING;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
    
    @Version
    private Long version;
    
    // Constructors, getters, setters, and business methods
    public void activate() {
        if (this.status != MerchantStatus.PENDING) {
            throw new BusinessException(
                "INVALID_STATE",
                String.format("Merchant %s is not PENDING. Current status: %s", 
                    this.id, this.status)
            );
        }
        this.status = MerchantStatus.ACTIVE;
    }
    
    public boolean isActive() {
        return this.status == MerchantStatus.ACTIVE;
    }
}