package com.order.validation.infrastructure.repository;

import com.order.validation.infrastructure.repository.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
