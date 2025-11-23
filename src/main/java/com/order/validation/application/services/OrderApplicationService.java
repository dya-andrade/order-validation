package com.order.validation.application.services;

import com.order.validation.api.dtos.CreateOrderRequest;
import com.order.validation.domain.entities.Order;
import com.order.validation.infrastructure.repository.CustomerRepository;
import com.order.validation.infrastructure.repository.OrderRepository;
import com.order.validation.infrastructure.repository.entities.OrderEntity;
import com.order.validation.infrastructure.repository.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public Order createOrder(CreateOrderRequest req) {
        Order order = Order.create(req, customerRepository);
        // map domain -> entity
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity saved = orderRepository.save(entity);

        return OrderMapper.toDomain(saved);
    }
}

