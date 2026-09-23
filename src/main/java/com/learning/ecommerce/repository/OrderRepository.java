package com.learning.ecommerce.repository;

import com.learning.ecommerce.entity.Order;
import com.learning.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserIdOrderByCreatedAtDesc(
            UUID userId
    );

    List<Order> findByStatus(
            OrderStatus status
    );
}