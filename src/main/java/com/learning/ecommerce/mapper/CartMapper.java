package com.learning.ecommerce.mapper;

import com.learning.ecommerce.dto.CartItemResponseDTO;
import com.learning.ecommerce.dto.CartResponseDTO;
import com.learning.ecommerce.entity.Cart;
import com.learning.ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemResponseDTO toCartItemResponse(
            CartItem item
    ) {

        BigDecimal subtotal = item.getUnitPrice()
                .multiply(
                        BigDecimal.valueOf(item.getQuantity())
                );

        return new CartItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                subtotal
        );
    }

    public CartResponseDTO toCartResponse(Cart cart) {

        List<CartItemResponseDTO> items =
                cart.getItems()
                        .stream()
                        .map(this::toCartItemResponse)
                        .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponseDTO::subtotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        return new CartResponseDTO(
                cart.getId(),
                cart.getUserId(),
                cart.getSessionToken(),
                items,
                total
        );
    }
}