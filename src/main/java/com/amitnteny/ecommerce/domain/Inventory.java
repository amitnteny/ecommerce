package com.amitnteny.ecommerce.domain;

import com.amitnteny.ecommerce.entity.InventoryEntity;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@AllArgsConstructor
public class Inventory {
    private Long productId;
    private Long availableQuantity;
    private BigDecimal price;
    private String productDescription;

    public Inventory(InventoryEntity entity) {
        this.productId = entity.getProductId();
        this.availableQuantity = entity.getAvailableQuantity();
        this.price = entity.getPrice();
        this.productDescription = entity.getProductDescription();
    }
}
