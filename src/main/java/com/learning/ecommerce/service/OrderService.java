package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.OrderRequestDTO;
import com.learning.ecommerce.dto.OrderResponseDTO;
import com.learning.ecommerce.entity.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDTO Order(
            UUID userId,
            OrderRequestDTO request
    );

    OrderResponseDTO createOrder(
            UUID userId,
            OrderRequestDTO request
    );

    OrderResponseDTO getOrderById(
            UUID orderId
    );

    List<OrderResponseDTO> getUserOrders(
            UUID userId
    );

    OrderResponseDTO updateOrderStatus(
            UUID orderId,
            OrderStatus status
    );

    OrderResponseDTO cancelOrder(
            UUID orderId
    );
}