package com.shery.inventory_service.dto;

public record InventoryResponse(
        Long id,
        Long productId,
        String productName,
        String sku,
        java.math.BigDecimal price,
        int quantityOnHand,
        int quantityReserved,
        int quantityAvailable,
        int reorderLevel,
        boolean lowStock,
        String warehouse
) {
}
