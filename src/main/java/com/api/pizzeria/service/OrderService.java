package com.api.pizzeria.service;

import com.api.pizzeria.persistence.entity.OrderEntity;
import com.api.pizzeria.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ONSITE = "O";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll(){
        return this.orderRepository.findAll();
    }

    public Optional<List<OrderEntity>> getTodayOrders(){
        LocalDateTime today = LocalDate.now().atTime(0, 0);
        return Optional.ofNullable(this.orderRepository.findAllByDateAfter(today));
    }

    public  Optional<List<OrderEntity>> getOutsideOrders(){
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return Optional.ofNullable(this.orderRepository.findAllByMethodIn(methods));
    }
}
