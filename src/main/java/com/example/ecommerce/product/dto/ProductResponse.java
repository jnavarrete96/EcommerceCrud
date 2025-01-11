package com.example.ecommerce.product.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
