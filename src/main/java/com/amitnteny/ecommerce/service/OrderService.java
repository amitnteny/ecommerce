package com.amitnteny.ecommerce.service;

import com.amitnteny.ecommerce.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order createOrder(Order order);

    List<Order> createBulkOrders(List<Order> orders);

    Order updateOrder(Order order);

    Order getOrderById(Long orderId);

    void deleteOrder(Long orderId);
}
