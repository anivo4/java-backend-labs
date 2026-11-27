package com.anivo4.exception;

public class ShopNotFoundException extends RuntimeException {
    public ShopNotFoundException(String message) {
        super(message);
    }
}