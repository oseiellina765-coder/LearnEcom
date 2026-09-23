package com.learning.ecommerce.service.impl;

import com.learning.ecommerce.dto.CategoryRequestDTO;
import com.learning.ecommerce.dto.CategoryResponseDTO;
import com.learning.ecommerce.entity.Category;
import com.learning.ecommerce.mapper.CategoryMapper;
import com.learning.ecommerce.repository.CategoryRepository;
import com.learning.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    ) {

        if (categoryRepository.existsByNameIgnoreCase(
                request.name()
        )) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        Category category =
                categoryMapper.toEntity(request);

        Category savedCategory =
                categoryRepository.save(category);

        return categoryMapper.toResponse(
                savedCategory
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(
            UUID id
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO>
    getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO>
    getActiveCategories() {

        return categoryRepository
                .findByActiveTrue()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponseDTO updateCategory(
            UUID id,
            CategoryRequestDTO request
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        categoryRepository
                .findByNameIgnoreCase(request.name())
                .ifPresent(existing -> {

                    if (!existing.getId()
                            .equals(category.getId())) {

                        throw new RuntimeException(
                                "Category name already exists"
                        );
                    }
                });

        categoryMapper.updateEntity(
                category,
                request
        );

        Category updatedCategory =
                categoryRepository.save(category);

        return categoryMapper.toResponse(
                updatedCategory
        );
    }

    @Override
    public void deleteCategory(UUID id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        categoryRepository.delete(category);
    }
}