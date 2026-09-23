package com.learning.ecommerce.mapper;

import com.learning.ecommerce.dto.AddressRequestDTO;
import com.learning.ecommerce.dto.AddressResponseDTO;
import com.learning.ecommerce.entity.Address;
import com.learning.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(
            AddressRequestDTO request,
            User user
    ) {

        return Address.builder()
                .user(user)
                .addressLine(request.addressLine())
                .city(request.city())
                .region(request.region())
                .country(request.country())
                .postalCode(request.postalCode())
                .phoneNumber(request.phoneNumber())
                .defaultAddress(
                        request.defaultAddress() != null
                                ? request.defaultAddress()
                                : false
                )
                .build();
    }

    public AddressResponseDTO toResponse(
            Address address
    ) {

        return new AddressResponseDTO(
                address.getId(),
                address.getUser().getId(),
                address.getAddressLine(),
                address.getCity(),
                address.getRegion(),
                address.getCountry(),
                address.getPostalCode(),
                address.getPhoneNumber(),
                address.getDefaultAddress()
        );
    }

    public void updateEntity(
            Address address,
            AddressRequestDTO request
    ) {

        address.setAddressLine(
                request.addressLine()
        );

        address.setCity(
                request.city()
        );

        address.setRegion(
                request.region()
        );

        address.setCountry(
                request.country()
        );

        address.setPostalCode(
                request.postalCode()
        );

        address.setPhoneNumber(
                request.phoneNumber()
        );

        if (request.defaultAddress() != null) {
            address.setDefaultAddress(
                    request.defaultAddress()
            );
        }
    }
}