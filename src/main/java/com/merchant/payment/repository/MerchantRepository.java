// src/main/java/com/merchant/payment/repository/MerchantRepository.java
package com.merchant.payment.repository;

import com.merchant.payment.domain.Merchant;
import com.merchant.payment.domain.MerchantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Merchant entity with custom queries and operations
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID>, 
                                           JpaSpecificationExecutor<Merchant> {
    
    // ==================== Basic CRUD Operations ====================
    
    /**
     * Find merchant by email (unique constraint)
     */
    Optional<Merchant> findByEmail(String email);
    
    /**
     * Check if merchant exists by email
     */
    boolean existsByEmail(String email);
    
    /**
     * Find merchants by status
     */
    List<Merchant> findByStatus(MerchantStatus status);
    
    /**
     * Find merchants by status with pagination
     */
    Page<Merchant> findByStatus(MerchantStatus status, Pageable pageable);
    
    // ==================== Custom Query Methods ====================
    

}