package com.example.ecommerce.product.service;

import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(String productId);
    void deleteProductById(String productId);
    void updateProduct(String productId, ProductRequest productRequest);
}
