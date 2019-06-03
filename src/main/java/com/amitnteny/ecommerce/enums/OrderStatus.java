package com.amitnteny.ecommerce.enums;

public enum OrderStatus {
    PLACED("PLACED"),
    PROCESSING("PROCESSING"),
    DISPATCHED("DISPATCHED"),
    OUT_FOR_DELIVERY("OUT_FOR_DELIVERY"),
    DELIVERED("DELIVERED");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public OrderStatus getStatusForString(String status) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.toString().equals(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }
}
