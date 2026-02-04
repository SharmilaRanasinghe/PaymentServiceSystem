package com.merchant.payment.repository;

import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.domain.PaymentTransaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

// repository/PaymentRepository.java
@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, UUID>,
        JpaSpecificationExecutor<PaymentTransaction> {

    
    boolean existsByMerchantIdAndStatus(UUID merchantId, PaymentStatus status);
}

