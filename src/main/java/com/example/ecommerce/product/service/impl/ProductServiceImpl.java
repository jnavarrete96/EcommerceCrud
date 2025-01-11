package com.example.ecommerce.product.service.impl;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.entity.ProductEntity;
import com.example.ecommerce.product.mapper.ProductMapper;
import com.example.ecommerce.product.repository.ProductRepository;
import com.example.ecommerce.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
