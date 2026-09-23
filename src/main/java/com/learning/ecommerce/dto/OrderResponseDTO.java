package com.learning.ecommerce.dto;

import com.learning.ecommerce.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(

        UUID id,

        UUID userId,

        UUID shippingAddressId,

        OrderStatus status,

        BigDecimal totalAmount,

        List<OrderItemResponseDTO> items,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}