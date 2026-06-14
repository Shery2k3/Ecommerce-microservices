package com.shery.product_service.mapper;

import com.shery.product_service.dto.category.CategoryResponse;
import com.shery.product_service.dto.category.CreateCategoryRequest;
import com.shery.product_service.entity.Category;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
    Category toEntity(CreateCategoryRequest request);
}
