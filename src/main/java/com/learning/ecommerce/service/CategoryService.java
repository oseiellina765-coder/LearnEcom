package com.learning.ecommerce.service;

import com.learning.ecommerce.dto.CategoryRequestDTO;
import com.learning.ecommerce.dto.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    );

    CategoryResponseDTO getCategoryById(
            UUID id
    );

    List<CategoryResponseDTO> getAllCategories();

    List<CategoryResponseDTO> getActiveCategories();

    CategoryResponseDTO updateCategory(
            UUID id,
            CategoryRequestDTO request
    );

    void deleteCategory(UUID id);
}