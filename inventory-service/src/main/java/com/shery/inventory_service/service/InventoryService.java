package com.shery.inventory_service.service;

import com.shery.inventory_service.client.ProductClient;
import com.shery.inventory_service.dto.*;
import com.shery.inventory_service.entity.InventoryItem;
import com.shery.inventory_service.mapper.InventoryMapper;
import com.shery.inventory_service.repository.InventoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductClient productClient;

    public InventoryService(InventoryItemRepository inventoryItemRepository, InventoryMapper inventoryMapper, ProductClient productClient) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryMapper = inventoryMapper;
        this.productClient = productClient;
    }

    @Transactional
    public InventoryResponse createStock(CreateInventoryRequest request) {
        ProductClientResponse product = productClient.getProduct(request.productId());

        if (inventoryItemRepository.existsByProductId(request.productId())) {
            throw new RuntimeException("Inventory already exists for product " + request.productId());
        }
        InventoryItem saved = inventoryItemRepository.save(inventoryMapper.toEntity(request));
        return inventoryMapper.toResponse(saved, product);
    }

    @Transactional(readOnly = true)
    public InventoryResponse getByProductId(Long productId) {
        InventoryItem item = findItem(productId);
        ProductClientResponse product = productClient.getProduct(productId);
        return inventoryMapper.toResponse(item, product);
    }

    @Transactional
    public InventoryResponse adjustStock(Long productId, int quantityChange) {
        InventoryItem item = findItem(productId);
        int newOnHand = item.getQuantityOnHand() + quantityChange;
        if (newOnHand < 0) {
            throw new RuntimeException("Cannot reduce on-hand below zero");
        }
        item.setQuantityOnHand(newOnHand);
        InventoryItem saved = inventoryItemRepository.save(item);
        return inventoryMapper.toResponse(saved, productClient.getProduct(productId));
    }

    @Transactional
    public InventoryResponse reserve(Long productId, int quantity) {
        InventoryItem item = findItem(productId);
        int available = item.getQuantityOnHand() - item.getQuantityReserved();
        if (quantity > available) {
            throw new RuntimeException(
                    "Not enough stock for product " + productId + ": available " + available + ", requested " + quantity);
        }
        item.setQuantityReserved(item.getQuantityReserved() + quantity);
        InventoryItem saved = inventoryItemRepository.save(item);
        return inventoryMapper.toResponse(saved, productClient.getProduct(productId));
    }

    @Transactional
    public InventoryResponse release(Long productId, int quantity) {
        InventoryItem item = findItem(productId);
        int newReserved = item.getQuantityReserved() - quantity;
        item.setQuantityReserved(Math.max(newReserved, 0));
        InventoryItem saved = inventoryItemRepository.save(item);
        return inventoryMapper.toResponse(saved, productClient.getProduct(productId));
    }

    private InventoryItem findItem(Long productId) {
        return inventoryItemRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with product id: " + productId));
    }

}
