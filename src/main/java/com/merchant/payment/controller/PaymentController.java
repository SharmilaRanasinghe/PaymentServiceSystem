package com.merchant.payment.controller;

import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.dto.*;
import com.merchant.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        log.info("Processing payment request order id : {}", request.getOrderId());
        PaymentResponse paymentResponse = paymentService.createPayment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(paymentResponse, "Order created successfully"));
    }

    @GetMapping()
    @Operation(summary = "List payments with filtering and pagination")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> listPayments(@Parameter(description = "Filter by merchant ID")
                                                                               @RequestParam(required = false) UUID merchantId,

                                                                           @Parameter(description = "Filter by payment status")
                                                                               @RequestParam(required = false) PaymentStatus status,

                                                                           @Parameter(description = "Page number (0-based)", example = "0")
                                                                               @RequestParam(defaultValue = "0") int page,

                                                                           @Parameter(description = "Page size", example = "10")
                                                                               @RequestParam(defaultValue = "10") int size) {
        log.info("Get payment requests for merchant id : {}", merchantId);
        Page<PaymentResponse> list = paymentService.listPayments(merchantId, status, page, size);

        return ResponseEntity
                .ok(ApiResponse.success(list, "Payment list successfully"));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updateStatus(@PathVariable UUID paymentId, @RequestBody UpdatePaymentStatusRequest request) {
        log.info("PUT /api/v1/payments/{}/update", paymentId);

        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, request);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Payment status updated successfully"));
    }
}