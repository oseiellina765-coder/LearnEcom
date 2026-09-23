package com.learning.ecommerce.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponseDTO(

        UUID id,
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
}