package com.order.validation.api.controllers;

import com.order.validation.api.dtos.CreateCustomerRequest;
import com.order.validation.application.services.CustomerApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerApplicationService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCustomerRequest request) {
        service.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}