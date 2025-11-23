package com.order.validation.api.controllers;

import com.order.validation.api.dtos.CreateOrderRequest;
import com.order.validation.application.services.OrderApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderApplicationService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateOrderRequest req) {
        service.createOrder(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
