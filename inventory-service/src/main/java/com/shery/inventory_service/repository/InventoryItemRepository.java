package com.shery.inventory_service.repository;

import com.shery.inventory_service.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}
