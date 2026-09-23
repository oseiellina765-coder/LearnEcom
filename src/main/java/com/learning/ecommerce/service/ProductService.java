package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.ProductRequestDTO;
import com.learning.ecommerce.dto.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    ProductResponseDTO getProductById(UUID id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO updateProduct(
            UUID id,
            ProductRequestDTO request
    );

    void deleteProduct(UUID id);

    List<ProductResponseDTO> getProductsByCategory(
            String category
    );

    List<ProductResponseDTO> getProductsByBrand(
            String brand
    );

    List<ProductResponseDTO> getActiveProducts();

    List<ProductResponseDTO> searchProducts(
            String name
    );
}