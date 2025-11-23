package com.order.validation.application.services;

import com.order.validation.api.dtos.CreateCustomerRequest;
import com.order.validation.domain.entities.Customer;
import com.order.validation.infrastructure.repository.CustomerRepository;
import com.order.validation.infrastructure.repository.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerApplicationService {

    private final CustomerRepository repo;

    public void createCustomer(CreateCustomerRequest req) {
        // domínio sem JPA
        Customer customer = new Customer(null, req.name(), req.active());

        // conversão domínio → infra (entity)
        var entity = CustomerMapper.toEntity(customer);

        repo.save(entity);
    }
}