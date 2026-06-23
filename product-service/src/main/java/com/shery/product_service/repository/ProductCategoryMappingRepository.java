package com.shery.product_service.repository;

import com.shery.product_service.entity.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, Long> {
    List<ProductCategoryMapping> findByProductId(Long productId);
}
