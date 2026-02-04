// src/main/java/com/merchant/payment/dto/request/CreatePaymentRequest.java
package com.merchant.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.merchant.payment.domain.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for creating a new payment transaction")
public class CreatePaymentRequest {
    
    @NotNull(message = "Merchant ID is required")
    @Schema(
        description = "Unique identifier of the merchant",
        example = "123e4567-e89b-12d3-a456-426614174000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID merchantId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 12, fraction = 2, message = "Amount must have up to 12 integer digits and 2 decimal places")
    @Schema(
        description = "Payment amount",
        example = "2500.00",
        minimum = "0.01",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal amount;
    
    @NotNull(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters (ISO 4217 code)")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 currency code (e.g., LKR, USD)")
    @Schema(
        description = "ISO 4217 currency code",
        example = "LKR",
        pattern = "^[A-Z]{3}$",
        minLength = 3,
        maxLength = 3,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String currency;
    
    @NotNull(message = "Payment method is required")
    @Schema(
        description = "Payment method",
        example = "CARD",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private PaymentMethod paymentMethod;
    
    @Schema(
        description = "Payment description or reference",
        example = "Order #12345",
        maxLength = 255
    )
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
    @Schema(
        description = "Customer email for receipt",
        example = "customer@example.com"
    )
    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$", message = "Customer email must be valid")
    private String customerEmail;
    
    @Schema(
        description = "Customer reference ID",
        example = "cust_12345",
        maxLength = 100
    )
    @Size(max = 100, message = "Customer reference must not exceed 100 characters")
    private String customerReference;
    
    @Schema(
        description = "Order ID or reference from merchant system",
        example = "ORD-2024-001",
        maxLength = 100
    )
    @Size(max = 100, message = "Order ID must not exceed 100 characters")
    private String orderId;
    
    @Schema(
        description = "Metadata for additional payment information (JSON)",
        example = "{\"invoice_id\": \"INV-001\", \"items\": [{\"name\": \"Product 1\", \"quantity\": 2}]}"
    )
    private String metadata;
    
    @Schema(
        description = "IP address of the customer (for fraud detection)",
        example = "192.168.1.100"
    )
    @Pattern(regexp = "^$|^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "IP address must be valid")
    private String customerIp;
    
    @Schema(
        description = "Device fingerprint or session ID",
        example = "device_fp_xyz123"
    )
    @Size(max = 255, message = "Device fingerprint must not exceed 255 characters")
    private String deviceFingerprint;
    
    // Card payment specific fields
    @Schema(
        description = "Card token (for tokenized payments)",
        example = "tok_visa_123456"
    )
    @Size(max = 100, message = "Card token must not exceed 100 characters")
    private String cardToken;
    
    @Schema(
        description = "Last 4 digits of card number",
        example = "4242",
        pattern = "^\\d{4}$"
    )
    @Pattern(regexp = "^$|^\\d{4}$", message = "Last 4 digits must be exactly 4 digits")
    private String lastFourDigits;
    
    // Wallet payment specific fields
    @Schema(
        description = "Wallet provider (e.g., PAYPAL, VENMO)",
        example = "PAYPAL"
    )
    private String walletProvider;
    
    @Schema(
        description = "Wallet token or reference",
        example = "wallet_ref_789"
    )
    @Size(max = 100, message = "Wallet token must not exceed 100 characters")
    private String walletToken;
    
    // Bank transfer specific fields
    @Schema(
        description = "Bank account reference",
        example = "acc_987654"
    )
    @Size(max = 100, message = "Bank reference must not exceed 100 characters")
    private String bankReference;
    
    @Schema(
        description = "Transaction reference for bank transfer",
        example = "BANK-REF-2024-001"
    )
    @Size(max = 100, message = "Transaction reference must not exceed 100 characters")
    private String transactionReference;
    
    @Schema(
        description = "Scheduled payment date (future dated payments)",
        example = "2024-12-31T23:59:59Z"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String scheduledDate;
}