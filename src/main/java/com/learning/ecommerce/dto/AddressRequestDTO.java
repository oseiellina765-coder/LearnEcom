package com.learning.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(

        @NotBlank(message = "Address line is required")
        String addressLine,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Region is required")
        String region,

        @NotBlank(message = "Country is required")
        String country,

        String postalCode,

        String phoneNumber,

        Boolean defaultAddress

) {
}