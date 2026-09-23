package com.learning.ecommerce.controller;

import com.learning.ecommerce.dto.CartItemRequestDTO;
import com.learning.ecommerce.dto.CartResponseDTO;
import com.learning.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getUserCart(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                cartService.getCartByUserId(userId)
        );
    }

    @GetMapping("/session/{sessionToken}")
    public ResponseEntity<CartResponseDTO> getSessionCart(
            @PathVariable String sessionToken
    ) {

        return ResponseEntity.ok(
                cartService.getCartBySessionToken(sessionToken)
        );
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String sessionToken,
            @Valid @RequestBody CartItemRequestDTO request
    ) {

        return ResponseEntity.ok(
                cartService.addItem(
                        userId,
                        sessionToken,
                        request
                )
        );
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @PathVariable UUID cartItemId,
            @Valid @RequestBody CartItemRequestDTO request
    ) {

        return ResponseEntity.ok(
                cartService.updateItem(
                        cartItemId,
                        request
                )
        );
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID cartItemId
    ) {

        cartService.removeItem(cartItemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(
            @PathVariable UUID cartId
    ) {

        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }
}