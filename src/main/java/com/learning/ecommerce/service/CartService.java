package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.CartItemRequestDTO;
import com.learning.ecommerce.dto.CartResponseDTO;

import java.util.UUID;

public interface CartService {

    CartResponseDTO getCartByUserId(UUID userId);

    CartResponseDTO getCartBySessionToken(String sessionToken);

    CartResponseDTO addItem(
            UUID userId,
            String sessionToken,
            CartItemRequestDTO request
    );

    CartResponseDTO updateItem(
            UUID cartItemId,
            CartItemRequestDTO request
    );

    void removeItem(UUID cartItemId);

    void clearCart(UUID cartId);
}