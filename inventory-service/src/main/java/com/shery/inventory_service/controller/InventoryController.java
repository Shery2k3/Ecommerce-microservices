package com.shery.inventory_service.controller;

import com.shery.inventory_service.dto.AdjustStockRequest;
import com.shery.inventory_service.dto.CreateInventoryRequest;
import com.shery.inventory_service.dto.InventoryResponse;
import com.shery.inventory_service.dto.ReserveStockRequest;
import com.shery.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> create(@Valid @RequestBody CreateInventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createStock(request));
    }

    @GetMapping("/{productId}")
    public InventoryResponse getByProduct(@PathVariable Long productId) {
        return inventoryService.getByProductId(productId);
    }

    @PatchMapping("/{productId}/adjust")
    public InventoryResponse adjust(@PathVariable Long productId,
                                    @Valid @RequestBody AdjustStockRequest request) {
        return inventoryService.adjustStock(productId, request.quantityChange());
    }

    @PostMapping("/{productId}/reserve")
    public InventoryResponse reserve(@PathVariable Long productId,
                                     @Valid @RequestBody ReserveStockRequest request) {
        return inventoryService.reserve(productId, request.quantity());
    }

    @PostMapping("/{productId}/release")
    public InventoryResponse release(@PathVariable Long productId,
                                     @Valid @RequestBody ReserveStockRequest request) {
        return inventoryService.release(productId, request.quantity());
    }

}
