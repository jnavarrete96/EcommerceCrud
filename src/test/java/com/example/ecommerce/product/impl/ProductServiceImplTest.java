package com.example.ecommerce.product.impl;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.GeneralException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.dto.ProductResponse;
import com.example.ecommerce.product.entity.ProductEntity;
import com.example.ecommerce.product.mapper.ProductMapper;
import com.example.ecommerce.product.repository.ProductRepository;
import com.example.ecommerce.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private ProductResponse productResponse;

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

        productResponse = new ProductResponse();
        productResponse.setProductId("P001");
        productResponse.setName("Product 1");
        productResponse.setDescription("Description");
        productResponse.setPrice(50.0);
        productResponse.setStock(100);
        productResponse.setCreatedAt(LocalDateTime.now());
        productResponse.setUpdatedAt(LocalDateTime.now());
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

    @Test
    void testGetAllProductsSuccess() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(productEntity);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.mapToProductResponse(productEntity)).thenReturn(productResponse);

        List<ProductResponse> result = productService.getAllProducts();

        assertNotNull(result);
    }

    @Test
    void testGetAllGeneralError(){
        when(productRepository.findAll()).thenThrow(new GeneralException("Database error"));
        assertThrows(GeneralException.class, () -> {
            productService.getAllProducts();
        });
    }

    @Test
    void testGetProductByIdSuccess() {
        when(productRepository.findByProductId("P001")).thenReturn(Optional.of(productEntity));
        when(productMapper.mapToProductResponse(productEntity)).thenReturn(productResponse);
        ProductResponse result = productService.getProductById("P001");

        assertNotNull(result);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findByProductId("P002")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById("P002");
        });
    }

    @Test
    void testGetProductByIdGeneralError() {
        when(productRepository.findByProductId(anyString())).thenThrow(new GeneralException("Database error"));
        assertThrows(GeneralException.class, () -> {
            productService.getProductById("P001");
        });
    }

    @Test
    void testDeleteProductByIdSuccess() {
        when(productRepository.findByProductId("P001")).thenReturn(Optional.of(productEntity));
        productService.deleteProductById("P001");

        verify(productRepository, times(1)).delete(productEntity);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        when(productRepository.findByProductId("P002")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProductById("P002");
        });
    }

    @Test
    void testDeleteProductByIdGeneralError() {
        when(productRepository.findByProductId("P001")).thenThrow(new GeneralException("Database error"));
        assertThrows(GeneralException.class, () -> {
            productService.deleteProductById("P001");
        });
    }

    @Test
    void testUpdateProductSuccess() {
        when(productRepository.findByProductId("P001")).thenReturn(Optional.of(productEntity));
        doNothing().when(productMapper).updateProductFromDto(any(ProductRequest.class), any(ProductEntity.class));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        productService.updateProduct("P001", productRequest);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findByProductId("P001")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct("P001", productRequest);
        });
    }

    @Test
    void testUpdateProductGeneralError(){
        when(productRepository.findByProductId("P001")).thenThrow(new GeneralException("Database error"));
        assertThrows(GeneralException.class, () -> {
            productService.updateProduct("P001", productRequest);
        });
    }
}
