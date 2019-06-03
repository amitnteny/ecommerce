package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.domain.Order;
import com.amitnteny.ecommerce.entity.OrderEntity;
import com.amitnteny.ecommerce.enums.OrderStatus;
import com.amitnteny.ecommerce.exceptions.ItemOutOfStockException;
import com.amitnteny.ecommerce.exceptions.OrderNotPresentException;
import com.amitnteny.ecommerce.repository.InventoryRepository;
import com.amitnteny.ecommerce.repository.OrderRepository;
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
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrder(Order order) {
        log.info("Creating a new Order for accountId={}, OrderDetails={}", order.getAccountId(), order);
        Long availableProductCount = inventoryRepository.countByProductId(order.getProductId());
        if (availableProductCount > 0 && availableProductCount >= order.getQuantity()) {
            BigDecimal orderPrice = inventoryRepository.findByProductId(order.getProductId()).get()
                    .getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
            OrderEntity orderEntity = getEntityFromOrder(order);
            orderEntity.setTotalAmount(orderPrice);
            orderEntity.setOrderStatus(OrderStatus.PLACED);
            OrderEntity placedOrderEntity = orderRepository.save(orderEntity);
            inventoryRepository.updateInventory(order.getProductId(), availableProductCount - order.getQuantity());
            return getOrderFromEntity(placedOrderEntity);
        } else {
            log.error("QUANTITY ORDERED IS MORE THAN AVAILABLE QUANTITY");
            throw new ItemOutOfStockException();
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order updateOrder(Order order) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findByOrderId(order.getOrderId());
        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = getEntityFromOrder(order);
            OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);
            return getOrderFromEntity(updatedOrderEntity);
        }
        log.error("No Order placed for orderId={}", order.getOrderId());
        throw new OrderNotPresentException();
    }

    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::getOrderFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findByOrderId(orderId);
        return getOrderFromEntity(orderEntityOptional.get());
    }

    @Override
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

    @Override
    public List<Order> createBulkOrders(List<Order> orders) {
        return orders.stream()
                .map(this::createOrder)
                .collect(Collectors.toList());
    }

    private Order getOrderFromEntity(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(orderEntity.getOrderId())
                .accountId(orderEntity.getAccountId())
                .productId(orderEntity.getProductId())
                .quantity(orderEntity.getQuantity())
                .totalAmount(orderEntity.getTotalAmount())
                .orderStatus(orderEntity.getOrderStatus())
                .description(orderEntity.getDescription())
                .timestamp(orderEntity.getTimestamp()).build();
    }

    private OrderEntity getEntityFromOrder(Order order) {
        return OrderEntity.builder()
                .orderId(order.getOrderId())
                .accountId(order.getAccountId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .description(order.getDescription())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
