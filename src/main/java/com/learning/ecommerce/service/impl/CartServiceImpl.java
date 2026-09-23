package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.CartItemRequestDTO;
import com.learning.ecommerce.dto.CartResponseDTO;
import com.learning.ecommerce.entity.Cart;
import com.learning.ecommerce.entity.CartItem;
import com.learning.ecommerce.entity.Product;
import com.learning.ecommerce.mapper.CartMapper;
import com.learning.ecommerce.repository.CartItemRepository;
import com.learning.ecommerce.repository.CartRepository;
import com.learning.ecommerce.repository.ProductRepository;
import com.learning.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartByUserId(UUID userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found for user"
                        )
                );

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartBySessionToken(
            String sessionToken
    ) {

        Cart cart = cartRepository
                .findBySessionToken(sessionToken)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found for session"
                        )
                );

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponseDTO addItem(
            UUID userId,
            String sessionToken,
            CartItemRequestDTO request
    ) {

        // 1. Find the product
        Product product = productRepository
                .findById(request.productId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"
                        )
                );

        // 2. Check whether product is active
        if (!product.getActive()) {
            throw new RuntimeException(
                    "Product is not available"
            );
        }

        // 3. Check stock
        if (product.getStockQuantity()
                < request.quantity()) {

            throw new RuntimeException(
                    "Insufficient product stock"
            );
        }

        // 4. Find or create cart
        Cart cart;

        if (userId != null) {

            cart = cartRepository
                    .findByUserId(userId)
                    .orElseGet(() -> {

                        Cart newCart = Cart.builder()
                                .userId(userId)
                                .build();

                        return cartRepository.save(newCart);
                    });

        } else if (sessionToken != null) {

            cart = cartRepository
                    .findBySessionToken(sessionToken)
                    .orElseGet(() -> {

                        Cart newCart = Cart.builder()
                                .sessionToken(sessionToken)
                                .build();

                        return cartRepository.save(newCart);
                    });

        } else {

            throw new IllegalArgumentException(
                    "Either userId or sessionToken is required"
            );
        }

        // 5. Check whether product is already in cart
        CartItem existingItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                product.getId()
                        )
                        .orElse(null);

        if (existingItem != null) {

            int newQuantity =
                    existingItem.getQuantity()
                            + request.quantity();

            // Check stock again
            if (product.getStockQuantity()
                    < newQuantity) {

                throw new RuntimeException(
                        "Insufficient product stock"
                );
            }

            existingItem.setQuantity(newQuantity);

            // Keep current product price
            existingItem.setUnitPrice(
                    product.getPrice()
            );

            cartItemRepository.save(existingItem);

        } else {

            // 6. Create new CartItem
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.quantity())
                    .unitPrice(product.getPrice())
                    .build();

            cart.addItem(newItem);

            cartItemRepository.save(newItem);
        }

        // 7. Return updated cart
        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponseDTO updateItem(
            UUID cartItemId,
            CartItemRequestDTO request
    ) {

        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"
                        )
                );

        Product product = item.getProduct();

        // Check product availability
        if (!product.getActive()) {
            throw new RuntimeException(
                    "Product is no longer available"
            );
        }

        // Check stock
        if (product.getStockQuantity()
                < request.quantity()) {

            throw new RuntimeException(
                    "Insufficient product stock"
            );
        }

        item.setQuantity(request.quantity());

        item.setUnitPrice(product.getPrice());

        cartItemRepository.save(item);

        return cartMapper.toCartResponse(
                item.getCart()
        );
    }

    @Override
    public void removeItem(UUID cartItemId) {

        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"
                        )
                );

        Cart cart = item.getCart();

        cart.removeItem(item);

        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart(UUID cartId) {

        Cart cart = cartRepository
                .findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"
                        )
                );

        cart.getItems().clear();

        cartRepository.save(cart);
    }
}