package com.example.productservice.service;

import com.example.productservice.client.CategoryClient;
import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.repository.ProductRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryClient categoryClient;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest request) {
        // Gọi Feign Client kiểm tra danh mục có tồn tại không
        try {
            CategoryResponse category = categoryClient.getCategoryById(request.getCategoryId());
            if (category == null) {
                throw new CategoryNotFoundException(request.getCategoryId());
            }
        } catch (FeignException.NotFound e) {
            throw new CategoryNotFoundException(request.getCategoryId());
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .categoryId(request.getCategoryId())
                .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategoryId())
                .build();
    }
}
