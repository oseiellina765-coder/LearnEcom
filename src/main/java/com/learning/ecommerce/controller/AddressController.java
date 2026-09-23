package com.learning.ecommerce.controller;

import com.learning.ecommerce.dto.AddressRequestDTO;
import com.learning.ecommerce.dto.AddressResponseDTO;
import com.learning.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDTO> createAddress(
            @PathVariable UUID userId,
            @Valid @RequestBody AddressRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        addressService.createAddress(
                                userId,
                                request
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                addressService.getAddressById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>>
    getUserAddresses(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                addressService.getUserAddresses(userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable UUID id,
            @Valid @RequestBody AddressRequestDTO request
    ) {

        return ResponseEntity.ok(
                addressService.updateAddress(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID id
    ) {

        addressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<AddressResponseDTO>
    setDefaultAddress(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                addressService.setDefaultAddress(id)
        );
    }
}