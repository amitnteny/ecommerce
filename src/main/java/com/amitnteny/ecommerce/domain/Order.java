package com.amitnteny.ecommerce.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class Order {
    private Long orderId;
    private Long accountId;
    private Long productId;
    private Long quantity;
    private BigDecimal totalAmount;
    private String description;
    private Long timestamp;
}
