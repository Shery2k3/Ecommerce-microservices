package com.shery.inventory_service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductClientResponse(
        Long id,
        String name,
        String sku,
        BigDecimal price
        ) {
}
