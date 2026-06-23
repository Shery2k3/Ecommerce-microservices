package com.shery.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AdjustStockRequest(
        @NotNull Long productId,
        @NotNull int quantityChange
) {
}
