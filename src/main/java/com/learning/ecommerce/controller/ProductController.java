package com.learning.ecommerce.controller;

import com.learning.ecommerce.dto.ProductRequestDTO;
import com.learning.ecommerce.dto.ProductResponseDTO;
import com.learning.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDTO request
    ) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable UUID id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>>
    getProductsByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(category)
        );
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductResponseDTO>>
    getProductsByBrand(
            @PathVariable String brand
    ) {

        return ResponseEntity.ok(
                productService.getProductsByBrand(brand)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>>
    getActiveProducts() {

        return ResponseEntity.ok(
                productService.getActiveProducts()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>>
    searchProducts(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                productService.searchProducts(name)
        );
    }
}