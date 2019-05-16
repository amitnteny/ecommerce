package com.amitnteny.ecommerce.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "INVENTORY")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "AVAILABLE_QUANTITY")
    private Long availableQuantity;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;
}
