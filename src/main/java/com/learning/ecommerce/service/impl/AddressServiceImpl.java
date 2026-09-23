package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.AddressRequestDTO;
import com.learning.ecommerce.dto.AddressResponseDTO;
import com.learning.ecommerce.entity.Address;
import com.learning.ecommerce.entity.User;
import com.learning.ecommerce.mapper.AddressMapper;
import com.learning.ecommerce.repository.AddressRepository;
import com.learning.ecommerce.repository.UserRepository;
import com.learning.ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponseDTO createAddress(
            UUID userId,
            AddressRequestDTO request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        if (Boolean.TRUE.equals(
                request.defaultAddress()
        )) {

            addressRepository
                    .findByUserIdAndDefaultAddressTrue(userId)
                    .ifPresent(address -> {
                        address.setDefaultAddress(false);
                        addressRepository.save(address);
                    });
        }

        Address address =
                addressMapper.toEntity(request, user);

        Address savedAddress =
                addressRepository.save(address);

        return addressMapper.toResponse(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(
            UUID id
    ) {

        Address address =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found"
                                )
                        );

        return addressMapper.toResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getUserAddresses(
            UUID userId
    ) {

        return addressRepository
                .findByUserId(userId)
                .stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponseDTO updateAddress(
            UUID id,
            AddressRequestDTO request
    ) {

        Address address =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                request.defaultAddress()
        )) {

            UUID userId =
                    address.getUser().getId();

            addressRepository
                    .findByUserIdAndDefaultAddressTrue(userId)
                    .ifPresent(defaultAddress -> {

                        if (!defaultAddress.getId()
                                .equals(address.getId())) {

                            defaultAddress
                                    .setDefaultAddress(false);

                            addressRepository.save(
                                    defaultAddress
                            );
                        }
                    });
        }

        addressMapper.updateEntity(
                address,
                request
        );

        Address updatedAddress =
                addressRepository.save(address);

        return addressMapper.toResponse(
                updatedAddress
        );
    }

    @Override
    public void deleteAddress(UUID id) {

        Address address =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found"
                                )
                        );

        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDTO setDefaultAddress(
            UUID id
    ) {

        Address address =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found"
                                )
                        );

        UUID userId =
                address.getUser().getId();

        addressRepository
                .findByUserIdAndDefaultAddressTrue(userId)
                .ifPresent(currentDefault -> {

                    currentDefault
                            .setDefaultAddress(false);

                    addressRepository.save(
                            currentDefault
                    );
                });

        address.setDefaultAddress(true);

        Address savedAddress =
                addressRepository.save(address);

        return addressMapper.toResponse(savedAddress);
    }
}