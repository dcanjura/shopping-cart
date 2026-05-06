package com.product.service.repository;

import com.product.service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product getProductById(Long id);
}
