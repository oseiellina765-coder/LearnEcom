package com.learning.ecommerce.mapper;

import com.learning.ecommerce.dto.CategoryRequestDTO;
import com.learning.ecommerce.dto.CategoryResponseDTO;
import com.learning.ecommerce.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(
            CategoryRequestDTO request
    ) {

        return Category.builder()
                .name(request.name())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .active(
                        request.active() != null
                                ? request.active()
                                : true
                )
                .build();
    }

    public CategoryResponseDTO toResponse(
            Category category
    ) {

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImageUrl(),
                category.getActive()
        );
    }

    public void updateEntity(
            Category category,
            CategoryRequestDTO request
    ) {

        category.setName(request.name());
        category.setDescription(request.description());
        category.setImageUrl(request.imageUrl());

        if (request.active() != null) {
            category.setActive(request.active());
        }
    }
}