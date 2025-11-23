package com.order.validation.infrastructure.repository;

import com.order.validation.infrastructure.repository.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> { }
