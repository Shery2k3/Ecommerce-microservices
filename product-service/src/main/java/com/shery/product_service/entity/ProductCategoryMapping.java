package com.shery.product_service.entity;

import com.shery.product_service.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_category_mapping",
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "category_id"})
)
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductCategoryMapping extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
