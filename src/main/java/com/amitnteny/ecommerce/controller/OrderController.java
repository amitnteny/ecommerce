package com.amitnteny.ecommerce.controller;

import com.amitnteny.ecommerce.domain.Order;
import com.amitnteny.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
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

    @PostMapping("/createOrder")
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PostMapping("/createBulkOrder")
    @ResponseBody
    public List<Order> createBulkOrder(@RequestBody List<Order> orders) {
        return orderService.createBulkOrders(orders);
    }

    @PutMapping("/update")
    @ResponseBody
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @GetMapping("/getOrder/{orderId}")
    @ResponseBody
    public Order getOrder(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/delete/{orderID}")
    public void deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
