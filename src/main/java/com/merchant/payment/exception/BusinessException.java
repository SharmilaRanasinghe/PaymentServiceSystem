// src/main/java/com/merchant/payment/domain/exception/BusinessException.java
package com.merchant.payment.exception;

import lombok.Getter;

/**
 * Base business exception for all business rule violations
 * Extend this for specific business errors
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final String userMessage;
    private final Object[] parameters;
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = message;
        this.parameters = new Object[0];
    }
    
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.userMessage = message;
        this.parameters = new Object[0];
    }
    
    public BusinessException(String errorCode, String message, Object... parameters) {
        super(formatMessage(message, parameters));
        this.errorCode = errorCode;
        this.userMessage = message;
        this.parameters = parameters;
    }
    
    public BusinessException(String errorCode, String message, Throwable cause, Object... parameters) {
        super(formatMessage(message, parameters), cause);
        this.errorCode = errorCode;
        this.userMessage = message;
        this.parameters = parameters;
    }
    
    private static String formatMessage(String message, Object... parameters) {
        if (parameters == null || parameters.length == 0) {
            return message;
        }
        return String.format(message, parameters);
    }
    
    /**
     * Creates a formatted error message with parameters
     */
    public String getFormattedMessage() {
        return formatMessage(this.userMessage, this.parameters);
    }
}