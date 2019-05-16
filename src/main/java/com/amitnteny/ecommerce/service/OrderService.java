package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.exceptions.ItemOutOfStockException;
import com.amitnteny.ecommerce.repository.InventoryRepository;
import com.amitnteny.ecommerce.repository.OrderRepository;
import com.amitnteny.ecommerce.domain.Order;
import com.amitnteny.ecommerce.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    private OrderRepository orderRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String createOrder(Order order) {
        Long availableProductCount = inventoryRepository.countByProductId(order.getProductId());
        if (availableProductCount > 0 && availableProductCount >= order.getQuantity()) {
            BigDecimal orderPrice = inventoryRepository.findByProductId(order.getProductId()).get()
                    .getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
            OrderEntity orderEntity = getEntityFromOrder(order);
            orderEntity.setTotalAmount(orderPrice);
            OrderEntity placedOrder = orderRepository.save(orderEntity);
            inventoryRepository.updateInventory(order.getProductId(), availableProductCount - order.getQuantity());
            return "SUCCESS";
        } else {
            log.error("QUANTITY ORDERED IS MORE THAN AVAILABLE QUANTITY");
            throw new ItemOutOfStockException();
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
                .description(o.getDescription())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public List<String> createBulkOrders(List<Order> orders) {
        List<String> orderSummary = orders.stream()
                .map(this::createOrder)
                .collect(Collectors.toList());
        return orderSummary;
    }
}
