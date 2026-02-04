package com.merchant.payment.controller;

import com.merchant.payment.dto.ApiResponse;
import com.merchant.payment.dto.CreateMerchantRequest;
import com.merchant.payment.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.merchant.payment.dto.MerchantResponse;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
@Slf4j
public class MerchantController {
    private final MerchantService merchantService;
    @PostMapping
    public ResponseEntity<ApiResponse<MerchantResponse>> createMerchant(@Valid @RequestBody CreateMerchantRequest request) {
        log.info("Creating merchant: {}", request.getEmail());
        MerchantResponse merchantResponse = merchantService.createMerchant(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(merchantResponse, "Merchant created successfully"));
    }

}