package com.merchant.payment.service;

import com.merchant.payment.domain.Merchant;
import com.merchant.payment.domain.MerchantStatus;
import com.merchant.payment.dto.CreateMerchantRequest;
import com.merchant.payment.dto.MerchantResponse;
import com.merchant.payment.exception.BusinessException;
import com.merchant.payment.exception.ResourceNotFoundException;
import com.merchant.payment.repository.MerchantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

// service/MerchantService.java
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantResponse createMerchant(CreateMerchantRequest request) {
        log.info("Creating merchant with email: {}", request.getEmail());
        
        Merchant merchant = Merchant.builder()
            .name(request.getName())
            .email(request.getEmail())
            .status(MerchantStatus.PENDING)
            .build();
        
        merchant = merchantRepository.save(merchant);
        log.info("Created merchant with id: {}", merchant.getId());
        
        return MerchantResponse.fromEntity(merchant);
    }
    
    public MerchantResponse activateMerchant(UUID merchantId) {
        log.info("Activating merchant with id: {}", merchantId);
        
        Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "MERCHANT_NOT_FOUND",
                String.format("Merchant with id %s not found", merchantId)));
        
        merchant.activate();
        merchant = merchantRepository.save(merchant);
        log.info("Merchant {} activated successfully", merchantId);
        
        return MerchantResponse.fromEntity(merchant);
    }
}

