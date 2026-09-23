package com.learning.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(

        UUID id,

        String name,

        String description,

        BigDecimal price,

        Integer stockQuantity,

        String sku,

        String category,

        String brand,

        String imageUrl,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}