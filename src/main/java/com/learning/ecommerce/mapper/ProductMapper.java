package com.learning.ecommerce.mapper;

import com.learning.ecommerce.dto.ProductRequestDTO;
import com.learning.ecommerce.dto.ProductResponseDTO;
import com.learning.ecommerce.entity.Category;
import com.learning.ecommerce.entity.Product;
import com.learning.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public Product toEntity(ProductRequestDTO request) {

        Category category = categoryRepository
                .findByNameIgnoreCase(request.category())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found: " + request.category()
                        )
                );

        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .sku(request.sku())
                .category(category)
                .brand(request.brand())
                .imageUrl(request.imageUrl())
                .active(
                        request.active() != null
                                ? request.active()
                                : true
                )
                .build();
    }

    public ProductResponseDTO toResponse(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getSku(),
                product.getCategory().getName(),
                product.getBrand(),
                product.getImageUrl(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public void updateEntity(
            Product product,
            ProductRequestDTO request
    ) {

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setSku(request.sku());

        Category category = categoryRepository
                .findByNameIgnoreCase(request.category())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found: " + request.category()
                        )
                );

        product.setCategory(category);

        product.setBrand(request.brand());
        product.setImageUrl(request.imageUrl());

        if (request.active() != null) {
            product.setActive(request.active());
        }
    }
}