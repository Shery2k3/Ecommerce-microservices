package com.shery.product_service.service;

import com.shery.product_service.dto.category.CategoryResponse;
import com.shery.product_service.dto.category.CreateCategoryRequest;
import com.shery.product_service.entity.Category;
import com.shery.product_service.mapper.CategoryMapper;
import com.shery.product_service.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new RuntimeException("Category with name already exists");
        }

        Category category = categoryRepository.save(categoryMapper.toEntity(request));
        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
