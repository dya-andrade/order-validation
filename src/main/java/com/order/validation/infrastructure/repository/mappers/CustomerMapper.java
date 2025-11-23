package com.order.validation.infrastructure.repository.mappers;

import com.order.validation.domain.entities.Customer;
import com.order.validation.infrastructure.repository.entities.CustomerEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {

    public static CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                null,
                customer.getName(),
                customer.isActive()
        );
    }

    public static Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getActive()
        );
    }
}
