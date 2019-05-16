package com.amitnteny.ecommerce.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@Table(name = "ORDERS")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @JoinColumn(name = "ACCOUNT_ID")
    private Long accountId;

    @JoinColumn(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TIMESTAMP_OF_ORDER")
    private Long timestamp;
}
