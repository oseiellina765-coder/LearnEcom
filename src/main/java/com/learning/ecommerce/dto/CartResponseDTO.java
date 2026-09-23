package com.learning.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartResponseDTO(

        UUID id,

        UUID userId,

        String sessionToken,

        List<CartItemResponseDTO> items,

        BigDecimal total

) {
}