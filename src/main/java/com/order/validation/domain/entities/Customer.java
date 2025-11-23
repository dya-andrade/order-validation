package com.order.validation.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {

    private final Long id;
    private final String name;
    private final boolean active;
}
