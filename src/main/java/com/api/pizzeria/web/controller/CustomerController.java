package com.api.pizzeria.web.controller;

import com.api.pizzeria.persistence.entity.CustomerEntity;
import com.api.pizzeria.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerEntity> getByPhone(@RequestParam String phone){
        return this.customerService.getByPhone(phone).map(
                ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
