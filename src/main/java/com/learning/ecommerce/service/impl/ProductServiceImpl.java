package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.ProductRequestDTO;
import com.learning.ecommerce.dto.ProductResponseDTO;
import com.learning.ecommerce.entity.Product;
import com.learning.ecommerce.mapper.ProductMapper;
import com.learning.ecommerce.repository.ProductRepository;
import com.learning.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(
            ProductRequestDTO request
    ) {

        if (request.sku() != null &&
                productRepository.existsBySku(request.sku())) {

            throw new RuntimeException(
                    "Product with SKU already exists"
            );
        }

        Product product = productMapper.toEntity(request);

        Product savedProduct =
                productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: " + id
                        )
                );

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDTO updateProduct(
            UUID id,
            ProductRequestDTO request
    ) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: " + id
                        )
                );

        if (request.sku() != null &&
                !request.sku().equals(product.getSku()) &&
                productRepository.existsBySku(request.sku())) {

            throw new RuntimeException(
                    "Another product already uses this SKU"
            );
        }

        productMapper.updateEntity(product, request);

        Product updatedProduct =
                productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: " + id
                        )
                );

        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(
            String category
    ) {

        return productRepository.findByCategory(category)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByBrand(
            String brand
    ) {

        return productRepository.findByBrand(brand)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getActiveProducts() {

        return productRepository.findByActiveTrue()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProducts(
            String name
    ) {

        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}