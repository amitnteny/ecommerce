package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.repository.InventoryRepository;
import com.amitnteny.ecommerce.repository.OrderRepository;
import com.amitnteny.ecommerce.domain.Order;
import com.amitnteny.ecommerce.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String saveOrder(Order order) {
        Long availableProductCount = inventoryRepository.countByProductId(order.getProductId());
        if (availableProductCount > 0 && availableProductCount >= order.getQuantity()) {
            OrderEntity entity = getEntityFromOrder(order);
            orderRepository.save(entity);
            inventoryRepository.updateInventory(order.getProductId(), availableProductCount - order.getQuantity());
            return "ORDER PLACED SUCCESSFULLY";
        } else {
            return "QUANTITY ORDERED IS MORE THAN AVAILABLE QUANTITY";
        }
    }

    public List<Order> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::getOrderFromEntity)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findByOrderId(orderId);
        return getOrderFromEntity(orderEntityOptional.get());
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findByOrderId(orderId);
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            Long currentlyAvailableQuantity = inventoryRepository.countByProductId(orderId);
            orderRepository.deleteByOrderId(orderId);
            inventoryRepository.updateInventory(orderEntity.getProductId(), currentlyAvailableQuantity + orderEntity.getQuantity());
        }
    }

    private Order getOrderFromEntity(OrderEntity o) {
        return Order.builder()
                .orderId(o.getOrderId())
                .accountId(o.getAccountId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .totalAmount(o.getTotalAmount())
                .description(o.getDescription())
                .timestamp(o.getTimestamp()).build();
    }

    private OrderEntity getEntityFromOrder(Order o) {
        return OrderEntity.builder()
                .orderId(o.getOrderId())
                .accountId(o.getAccountId())
                .productId(o.getProductId())
                .quantity(o.getQuantity())
                .totalAmount(inventoryRepository.findByProductId(o.getProductId()).get().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
                .description(o.getDescription())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
