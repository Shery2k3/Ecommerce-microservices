package com.shery.product_service.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        @NotBlank String name,
        String description,
        @NotBlank String sku,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal discountPercentage,
        List<Long> categoryIds
        ) {
}
