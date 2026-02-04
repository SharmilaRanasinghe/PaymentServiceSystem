package com.merchant.payment.repository;

import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.domain.PaymentTransaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// repository/spec/PaymentSpecification.java
public class PaymentSpecification {
    
    public static Specification<PaymentTransaction> filterPayments(
            UUID merchantId, PaymentStatus status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (merchantId != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("merchant").get("id"), merchantId));
            }
            
            if (status != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("status"), status));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}