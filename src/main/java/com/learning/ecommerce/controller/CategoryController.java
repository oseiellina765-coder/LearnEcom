package com.learning.ecommerce.controller;

import com.learning.ecommerce.dto.CategoryRequestDTO;
import com.learning.ecommerce.dto.CategoryResponseDTO;
import com.learning.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO>
    createCategory(
            @Valid @RequestBody CategoryRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        categoryService.createCategory(
                                request
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO>
    getCategoryById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>>
    getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponseDTO>>
    getActiveCategories() {

        return ResponseEntity.ok(
                categoryService.getActiveCategories()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO>
    updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequestDTO request
    ) {

        return ResponseEntity.ok(
                categoryService.updateCategory(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable UUID id
    ) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}