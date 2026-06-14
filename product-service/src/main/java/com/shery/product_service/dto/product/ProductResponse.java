package com.shery.product_service.dto.product;

import com.shery.product_service.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        BigDecimal discountPercentage,
        boolean active,
        List<CategoryResponse> categories,
        LocalDateTime createdAt
) {
}
