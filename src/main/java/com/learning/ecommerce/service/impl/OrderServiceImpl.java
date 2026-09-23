package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.OrderRequestDTO;
import com.learning.ecommerce.dto.OrderResponseDTO;
import com.learning.ecommerce.entity.*;
import com.learning.ecommerce.mapper.OrderMapper;
import com.learning.ecommerce.repository.AddressRepository;
import com.learning.ecommerce.repository.CartRepository;
import com.learning.ecommerce.repository.OrderRepository;
import com.learning.ecommerce.repository.UserRepository;
import com.learning.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO Order(
            UUID userId,
            OrderRequestDTO request
    ) {

        // 1. Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // 2. Find shipping address
        Address address = addressRepository
                .findById(request.shippingAddressId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shipping address not found"
                        )
                );

        // 3. Make sure address belongs to user
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "Shipping address does not belong to user"
            );
        }

        // 4. Find user's cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"
                        )
                );

        // 5. Make sure cart isn't empty
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Cannot create order from an empty cart"
            );
        }

        // 6. Create order
        Order order = Order.builder()
                .user(user)
                .shippingAddress(address)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        // 7. Convert CartItems → OrderItems
        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            // Check product is still available
            if (!product.getActive()) {
                throw new RuntimeException(
                        "Product is no longer available: "
                                + product.getName()
                );
            }

            // Check stock
            if (product.getStockQuantity()
                    < cartItem.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            // Use current product price
            BigDecimal unitPrice =
                    product.getPrice();

            BigDecimal subtotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(
                                    cartItem.getQuantity()
                            )
                    );

            // Create order item
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            order.addItem(orderItem);

            total = total.add(subtotal);

            // Reduce product stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            - cartItem.getQuantity()
            );
        }

        // 8. Set order total
        order.setTotalAmount(total);

        // 9. Save order
        Order savedOrder =
                orderRepository.save(order);

        // 10. Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        // 11. Return response
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponseDTO createOrder(UUID userId, OrderRequestDTO request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(
            UUID orderId
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getUserOrders(
            UUID userId
    ) {

        return orderRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(
            UUID orderId,
            OrderStatus status
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(status);

        Order updatedOrder =
                orderRepository.save(order);

        return orderMapper.toOrderResponse(
                updatedOrder
        );
    }

    @Override
    public OrderResponseDTO cancelOrder(
            UUID orderId
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED) {

            throw new RuntimeException(
                    "This order cannot be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order cancelledOrder =
                orderRepository.save(order);

        return orderMapper.toOrderResponse(
                cancelledOrder
        );
    }
}