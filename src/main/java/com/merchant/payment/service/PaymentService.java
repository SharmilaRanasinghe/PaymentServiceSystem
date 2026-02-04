package com.merchant.payment.service;

import com.merchant.payment.domain.Merchant;
import com.merchant.payment.domain.PaymentStatus;
import com.merchant.payment.domain.PaymentTransaction;
import com.merchant.payment.dto.CreatePaymentRequest;
import com.merchant.payment.dto.PaymentResponse;
import com.merchant.payment.exception.BusinessException;
import com.merchant.payment.exception.ResourceNotFoundException;
import com.merchant.payment.repository.MerchantRepository;
import com.merchant.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.UUID;

// service/PaymentService.java
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MerchantRepository merchantRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        log.info("Creating payment for merchant: {}", request.getMerchantId());

        Merchant merchant = merchantRepository.findById(request.getMerchantId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "MERCHANT_NOT_FOUND",
                String.format("Merchant with id %s not found", request.getMerchantId())));

        if (!merchant.isActive()) {
            throw new BusinessException(
                "MERCHANT_INACTIVE",
                String.format("Merchant %s is not active", merchant.getId())
            );
        }

        PaymentTransaction payment = PaymentTransaction.builder()
            .merchant(merchant)
            .amount(request.getAmount())
            .currency(request.getCurrency().toUpperCase())
            .paymentMethod(request.getPaymentMethod())
            .status(PaymentStatus.INITIATED)
            .build();

        payment = paymentRepository.save(payment);
        log.info("Created payment with id: {}", payment.getId());

        return PaymentResponse.fromEntity(payment);
    }

//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public PaymentResponse updatePaymentStatus(UUID paymentId,
//                                               UpdatePaymentStatusRequest request) {
//        log.info("Updating payment {} status to {}", paymentId, request.getStatus());
//
//        PaymentTransaction payment = paymentRepository.findByIdForUpdate(paymentId)
//            .orElseThrow(() -> new ResourceNotFoundException(
//                "PAYMENT_NOT_FOUND",
//                String.format("Payment with id %s not found", paymentId)));
//
//        payment.updateStatus(request.getStatus());
//        payment = paymentRepository.save(payment);
//
//        eventPublisher.publishEvent(
//            new PaymentStatusChangedEvent(this, payment, request.getStatus()));
//
//        log.info("Payment {} status updated to {}", paymentId, request.getStatus());
//        return PaymentResponse.fromEntity(payment);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<PaymentResponse> listPayments(UUID merchantId,
//                                             PaymentStatus status,
//                                             int page,
//                                             int size) {
//        Specification<PaymentTransaction> spec = PaymentSpecification
//            .filterPayments(merchantId, status);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//
//        return paymentRepository.findAll(spec, pageable)
//            .map(PaymentResponse::fromEntity);
//    }
}