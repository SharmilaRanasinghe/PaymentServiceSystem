// src/main/java/com/merchant/payment/domain/exception/BusinessException.java
package com.merchant.payment.exception;

import lombok.Getter;

/**
 * Base business exception for all business rule violations
 * Extend this for specific business errors
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String errorCode;
    private final String userMessage;
    private final Object[] parameters;

    public ResourceNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = message;
        this.parameters = new Object[0];
    }
}