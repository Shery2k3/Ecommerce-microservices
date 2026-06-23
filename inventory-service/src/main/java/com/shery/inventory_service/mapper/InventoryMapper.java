package com.shery.inventory_service.mapper;

import com.shery.inventory_service.client.ProductClient;
import com.shery.inventory_service.dto.CreateInventoryRequest;
import com.shery.inventory_service.dto.InventoryResponse;
import com.shery.inventory_service.dto.ProductClientResponse;
import com.shery.inventory_service.entity.InventoryItem;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface InventoryMapper {

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "sku",         source = "product.sku")
    @Mapping(target = "price",       source = "product.price")
    @Mapping(target = "quantityAvailable",
            expression = "java(item.getQuantityOnHand() - item.getQuantityReserved())")
    @Mapping(target = "lowStock",
            expression = "java(item.getQuantityOnHand() <= item.getReorderLevel())")
    InventoryResponse toResponse(InventoryItem item, ProductClientResponse product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "quantityReserved", constant = "0")
    InventoryItem toEntity(CreateInventoryRequest request);
}
