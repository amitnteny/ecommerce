package com.amitnteny.ecommerce.exceptions;

public class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException() {
        super();
    }

    public ItemOutOfStockException(String message) {
        super(message);
    }

    public ItemOutOfStockException(String message, Throwable e) {
        super(message, e);
    }
}
