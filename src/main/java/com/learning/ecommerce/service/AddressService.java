package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.AddressRequestDTO;
import com.learning.ecommerce.dto.AddressResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponseDTO createAddress(
            UUID userId,
            AddressRequestDTO request
    );

    AddressResponseDTO getAddressById(
            UUID id
    );

    List<AddressResponseDTO> getUserAddresses(
            UUID userId
    );

    AddressResponseDTO updateAddress(
            UUID id,
            AddressRequestDTO request
    );

    void deleteAddress(UUID id);

    AddressResponseDTO setDefaultAddress(
            UUID id
    );
}