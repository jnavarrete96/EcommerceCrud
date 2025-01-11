package com.example.ecommerce.product.service.impl;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.dto.ProductResponse;
import com.example.ecommerce.product.entity.ProductEntity;
import com.example.ecommerce.product.mapper.ProductMapper;
import com.example.ecommerce.product.repository.ProductRepository;
import com.example.ecommerce.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        try{
            if (productRepository.existsByProductId(productRequest.getProductId())){
                throw new BadRequestException("A product with ID " + productRequest.getProductId() + " already exists.");
            }
            ProductEntity product = productMapper.mapToProductEntity(productRequest);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        }catch(GeneralException e){
            throw new GeneralException("Error while creating product: " + e.getMessage());
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        try{
            List<ProductEntity> products = productRepository.findAll();
            return products.stream()
                    .map(productMapper::mapToProductResponse)
                    .toList();
        }catch (GeneralException e){
            throw new GeneralException("Error getting products: " + e.getMessage());
        }
    }

    @Override
    public ProductResponse getProductById(String productId) {
        try {
            ProductEntity product = findProductByProductId(productId);
            return productMapper.mapToProductResponse(product);
        }catch (GeneralException e){
            throw new GeneralException("Error getting product: " + e.getMessage());
        }
    }

    @Override
    public void deleteProductById(String productId) {
        try {
            ProductEntity product = findProductByProductId(productId);
            productRepository.delete(product);
        }catch (GeneralException e){
            throw new GeneralException("Error getting product: " + e.getMessage());
        }
    }

    private ProductEntity findProductByProductId(String productId){
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found."));
    }
}
