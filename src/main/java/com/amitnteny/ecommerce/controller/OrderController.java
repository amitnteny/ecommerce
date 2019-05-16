package com.amitnteny.ecommerce.controller;

import com.amitnteny.ecommerce.domain.Order;
import com.amitnteny.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseBody
    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/create")
    public String createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/getOrder/{orderId}")
    @ResponseBody
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/delete/{orderID}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
