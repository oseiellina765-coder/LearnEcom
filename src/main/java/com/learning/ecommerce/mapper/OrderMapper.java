package com.learning.ecommerce.mapper;

import com.learning.ecommerce.dto.OrderItemResponseDTO;
import com.learning.ecommerce.dto.OrderResponseDTO;
import com.learning.ecommerce.entity.Order;
import com.learning.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemResponseDTO toOrderItemResponse(
            OrderItem item
    ) {

        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }

    public OrderResponseDTO toOrderResponse(
            Order order
    ) {

        List<OrderItemResponseDTO> items =
                order.getItems()
                        .stream()
                        .map(this::toOrderItemResponse)
                        .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getShippingAddress().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}