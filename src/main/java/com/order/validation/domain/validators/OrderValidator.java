package com.order.validation.domain.validators;

import com.order.validation.api.dtos.CreateOrderRequest;
import com.order.validation.domain.validators.interfaces.Invalid;
import com.order.validation.domain.validators.interfaces.Valid;
import com.order.validation.domain.validators.interfaces.ValidationResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class OrderValidator {

    public static ValidationResult validate(CreateOrderRequest req) {
        List<String> errors = new ArrayList<>();

        if (req.customerId() == null) errors.add("customerId is required");
        else if (req.customerId() < 0) errors.add("customerId < 0");

        if (req.amount() == null) errors.add("amount is required");

        if (req.orderDate() == null) errors.add("orderDate is required");
        else if (req.orderDate().isAfter(LocalDateTime.now().plusMinutes(5)))
            errors.add("orderDate > 5min");

        return errors.isEmpty() ? new Valid() : new Invalid(errors);
    }
}
