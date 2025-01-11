package com.example.ecommerce.product.impl;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.entity.ProductEntity;
import com.example.ecommerce.product.mapper.ProductMapper;
import com.example.ecommerce.product.repository.ProductRepository;
import com.example.ecommerce.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest productRequest;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        productRequest = new ProductRequest();
        productRequest.setProductId("P001");
        productRequest.setName("Product 1");
        productRequest.setDescription("Description");
        productRequest.setPrice(50.0);
        productRequest.setStock(100);

        productEntity = new ProductEntity();
        productEntity.setProductId("P001");
        productEntity.setName("Product 1");
        productEntity.setDescription("Description");
        productEntity.setPrice(50.0);
        productEntity.setStock(100);
    }

    @Test
    void testCreateProductSuccess() {
        when(productRepository.existsByProductId(anyString())).thenReturn(false);
        when(productMapper.mapToProductEntity(any(ProductRequest.class))).thenReturn(productEntity);
        productService.createProduct(productRequest);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testCreateProductAlreadyExists() {
        when(productRepository.existsByProductId(anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            productService.createProduct(productRequest);
        });
    }

    @Test
    void testCreateProductGeneralError() {
        when(productRepository.existsByProductId(anyString())).thenReturn(false);
        when(productMapper.mapToProductEntity(any(ProductRequest.class))).thenReturn(productEntity);
        doThrow(new GeneralException("Database error")).when(productRepository).save(any(ProductEntity.class));

        assertThrows(GeneralException.class, () -> {
            productService.createProduct(productRequest);
        });
    }
}
