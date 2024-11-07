package com.comestic.shop.exception;

public class InsufficientInventoryException extends Throwable {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}
