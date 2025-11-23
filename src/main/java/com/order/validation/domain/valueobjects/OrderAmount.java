package com.order.validation.domain.valueobjects;

import java.math.BigDecimal;

public record OrderAmount(BigDecimal value) {

    public OrderAmount {
        if (value == null) throw new IllegalArgumentException("Amount cannot be null");
        if (value.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be > 0");
    }
}