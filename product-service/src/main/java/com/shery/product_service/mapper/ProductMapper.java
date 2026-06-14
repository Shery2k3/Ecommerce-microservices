package com.shery.product_service.mapper;

import com.shery.product_service.dto.category.CategoryResponse;
import com.shery.product_service.dto.product.CreateProductRequest;
import com.shery.product_service.dto.product.ProductResponse;
import com.shery.product_service.entity.Product;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductMapper {
    @Mapping(target = "categories", source = "categories")
    ProductResponse toResponse(Product product, List<CategoryResponse> categories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product toEntity(CreateProductRequest request);
}
