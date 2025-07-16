package com.api.pizzeria.web.controller;

import com.api.pizzeria.persistence.entity.OrderEntity;
import com.api.pizzeria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderControler {
    private final OrderService orderService;

    @Autowired
    public OrderControler(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> get(){
        return ResponseEntity.ok(orderService.getAll());
    }
}
