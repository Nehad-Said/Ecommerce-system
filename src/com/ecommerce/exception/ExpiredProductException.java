package com.ecommerce.exception;

public class ExpiredProductException extends Exception {
    public ExpiredProductException(String message) {
        super(message);
    }
}
