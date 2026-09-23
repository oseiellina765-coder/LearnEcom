package com.learning.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(

        @NotBlank(message = "Category name is required")
        String name,

        String description,

        String imageUrl,

        Boolean active

) {
}