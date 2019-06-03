package com.amitnteny.ecommerce.domain;

import com.amitnteny.ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long orderId;
    private Long accountId;
    private Long productId;
    private Long quantity;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String description;
    private Long timestamp;
}
