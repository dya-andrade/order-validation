package com.order.validation.infrastructure.repository.mappers;

import com.order.validation.domain.entities.Order;
import com.order.validation.domain.valueobjects.OrderAmount;
import com.order.validation.infrastructure.repository.entities.OrderEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class OrderMapper {

    // Domínio → JPA Entity
    public static OrderEntity toEntity(Order domain) {
        return new OrderEntity(
                null,
                domain.getCustomerId(),
                domain.getAmount().value(),
                domain.getOrderDate()
        );
    }

    // JPA Entity → Domínio
    public static Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                new OrderAmount(entity.getAmount()),
                entity.getOrderDate()
        );
    }
}
