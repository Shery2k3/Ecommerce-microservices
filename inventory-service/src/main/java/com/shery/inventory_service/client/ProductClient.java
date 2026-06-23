package com.shery.inventory_service.client;

import com.shery.inventory_service.dto.ProductClientResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductClientResponse getProduct(Long productId) {
        return restTemplate.getForObject(
                "http://product-service/api/products/{id}",
                ProductClientResponse.class,
                productId
        );
    }
}
