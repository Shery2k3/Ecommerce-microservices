package com.shery.product_service.service;

import com.shery.product_service.dto.category.CategoryResponse;
import com.shery.product_service.dto.product.CreateProductRequest;
import com.shery.product_service.dto.product.ProductResponse;
import com.shery.product_service.entity.Category;
import com.shery.product_service.entity.Product;
import com.shery.product_service.entity.ProductCategoryMapping;
import com.shery.product_service.mapper.CategoryMapper;
import com.shery.product_service.mapper.ProductMapper;
import com.shery.product_service.repository.CategoryRepository;
import com.shery.product_service.repository.ProductCategoryMappingRepository;
import com.shery.product_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryMappingRepository productCategoryMappingRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductCategoryMappingRepository productCategoryMappingRepository, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryMappingRepository = productCategoryMappingRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new RuntimeException("Product with SKU already exists");
        }

        Product product = productRepository.save(productMapper.toEntity(request));

        // Attach (multiple) categories provided in the request to mapping table
        if (request.categoryIds() != null) {
            for (Long categoryId : request.categoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
                productCategoryMappingRepository.save(
                        ProductCategoryMapping.builder()
                                .product(product)
                                .category(category)
                                .build()
                );
            }
        }
        return buildResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::buildResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return buildResponse(product);
    }

    // Helper function to attach categories to product responses
    private ProductResponse buildResponse(Product product) {
        List<CategoryResponse> categories = productCategoryMappingRepository.findByProductId(product.getId())
                .stream()
                .map(m -> categoryMapper.toResponse(m.getCategory()))
                .toList();
        return productMapper.toResponse(product, categories);
    }
}
