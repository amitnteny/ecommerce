package com.amitnteny.ecommerce.exceptions;

public class OrderNotPresentException extends RuntimeException {
    public OrderNotPresentException() {
        super();
    }

    public OrderNotPresentException(String message) {
        super(message);
    }

    public OrderNotPresentException(String message, Throwable e) {
        super(message, e);
    }
}
