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
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> get(){
        return ResponseEntity.ok(this.orderService.getAll());
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayOrders(){
            return this.orderService.getTodayOrders()
                    .map(orders -> orders.isEmpty()
                            ? ResponseEntity.noContent().build()
                            : ResponseEntity.ok(orders))
                    .orElse(ResponseEntity.internalServerError().build());
     }

     @GetMapping("/outsite")
     public ResponseEntity<?> getOutside(){
         return this.orderService.getOutsideOrders()
                 .map(orders -> orders.isEmpty()
                         ? ResponseEntity.noContent().build()
                         : ResponseEntity.ok(orders))
                 .orElse(ResponseEntity.internalServerError().build());
     }


}
