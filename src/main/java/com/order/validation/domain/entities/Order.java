package com.order.validation.domain.entities;

import com.order.validation.api.dtos.CreateOrderRequest;
import com.order.validation.domain.exceptions.BusinessRuleException;
import com.order.validation.domain.validators.interfaces.Invalid;
import com.order.validation.domain.validators.OrderValidator;
import com.order.validation.domain.validators.interfaces.ValidationResult;
import com.order.validation.domain.valueobjects.OrderAmount;
import com.order.validation.infrastructure.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Order {

    private Long id;
    private final Long customerId;
    private final OrderAmount amount;
    private final LocalDateTime orderDate;

    public static Order create(
            CreateOrderRequest req, CustomerRepository customerRepo
    ) {
        // 1) validação pura
        ValidationResult vr = OrderValidator.validate(req);
        if (vr instanceof Invalid) {
            throw new IllegalArgumentException(String.join(", ", ((Invalid) vr).errors()));
        }

        // 2) regras de negócio que dependem de estado (repo)
        var optCustomer = customerRepo.findById(req.customerId());
        var customer = optCustomer.orElseThrow(() -> new BusinessRuleException("Customer not found"));
        if (!customer.isActive()) throw new BusinessRuleException("Customer inactive");

        var amount = new OrderAmount(req.amount());
        if (amount.value().compareTo(java.math.BigDecimal.valueOf(100)) < 0)
            throw new BusinessRuleException("Order minimum is 100");

        return new Order(null, req.customerId(), amount, req.orderDate());
    }
}