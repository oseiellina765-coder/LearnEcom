package com.learning.ecommerce.dto;

import java.util.UUID;

public record AddressResponseDTO(

        UUID id,

        UUID userId,

        String addressLine,

        String city,

        String region,

        String country,

        String postalCode,

        String phoneNumber,

        Boolean defaultAddress

) {
}