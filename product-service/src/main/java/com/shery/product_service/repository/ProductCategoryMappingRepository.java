package com.shery.product_service.repository;

import com.shery.product_service.entity.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, Long> {
    List<ProductCategoryMapping> findByProductId(Long productId);
}
