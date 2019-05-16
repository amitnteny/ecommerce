package com.amitnteny.ecommerce.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Inventory {
    private Long productId;
    private Long availableQuantity;
    private BigDecimal price;
    private String productDescription;
}
