package com.shery.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateInventoryRequest(
    @NotNull Long productId,
    @NotNull @Min(1) int quantityOnHand,
    @Min(0) int reorderLevel,
    String warehouse
) {
}
