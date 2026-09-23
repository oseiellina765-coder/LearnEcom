package com.learning.ecommerce.repository;

import com.learning.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {


    boolean existsBySku(String sku);

    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByActiveTrue();

    List<Product> findByNameContainingIgnoreCase(String name);
}