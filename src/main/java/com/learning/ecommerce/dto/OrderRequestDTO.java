package com.learning.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderRequestDTO(

        @NotNull(message = "Shipping address ID is required")
        UUID shippingAddressId

) {
}