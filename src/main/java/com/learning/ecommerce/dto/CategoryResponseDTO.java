package com.learning.ecommerce.dto;

import java.util.UUID;

public record CategoryResponseDTO(

        UUID id,

        String name,

        String description,

        String imageUrl,

        Boolean active

) {
}