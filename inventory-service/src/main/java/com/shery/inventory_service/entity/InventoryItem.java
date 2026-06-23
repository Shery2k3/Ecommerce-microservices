package com.shery.inventory_service.entity;

import com.shery.inventory_service.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "inventory_item", uniqueConstraints = @UniqueConstraint(columnNames = "product_id")
)
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class InventoryItem extends BaseEntity {

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(name = "quantity_on_hand", nullable = false)
    private int quantityOnHand;

    @Column(name = "quantity_reserved", nullable = false)
    @Builder.Default
    private int quantityReserved = 0;

    @Column(name = "reorder_level", nullable = false)
    @Builder.Default
    private int reorderLevel = 0;

    @Column
    private String warehouse;
}
