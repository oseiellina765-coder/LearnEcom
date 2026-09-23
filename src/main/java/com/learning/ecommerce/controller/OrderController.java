package com.learning.ecommerce.controller;

import com.learning.ecommerce.dto.OrderRequestDTO;
import com.learning.ecommerce.dto.OrderResponseDTO;
import com.learning.ecommerce.entity.OrderStatus;
import com.learning.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        orderService.createOrder(
                                userId,
                                request
                        )
                );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable UUID orderId
    ) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>>
    getUserOrders(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                orderService.getUserOrders(userId)
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO>
    updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status
    ) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        status
                )
        );
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO>
    cancelOrder(
            @PathVariable UUID orderId
    ) {

        return ResponseEntity.ok(
                orderService.cancelOrder(orderId)
        );
    }
}