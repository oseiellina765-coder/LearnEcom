package com.learning.ecommerce.repository;

import com.learning.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByUserId(UUID userId);

    Optional<Cart> findBySessionToken(String sessionToken);

    boolean existsByUserId(UUID userId);

    boolean existsBySessionToken(String sessionToken);
}