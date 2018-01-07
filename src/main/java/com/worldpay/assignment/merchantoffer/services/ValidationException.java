package com.worldpay.assignment.merchantoffer.services;

/**
 *
 * Validation exception
 *
 * @author Nicholas Goldsworthy
 * @since 2018-01-07
 */
public class ValidationException extends Exception{

    private Object data;

    public ValidationException(String message, Object data) {
        super(message);
        this.data = data;
    }
}
