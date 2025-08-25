package com.examly.springapp.controller;

import com.examly.springapp.dto.OrderResponseDTO;
import com.examly.springapp.model.Orders;
import com.examly.springapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(@RequestBody List<Long> cartItemIds) {
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            throw new RuntimeException("No items selected for checkout");
        }
        Orders order = orderService.placeOrder(cartItemIds);
        return ResponseEntity.ok(order);
    }

    @GetMapping()
    public List<Orders> getOrders() {
        return orderService.getUserOrders();
    }
    
    @GetMapping("/all")
    public List<OrderResponseDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    
}
