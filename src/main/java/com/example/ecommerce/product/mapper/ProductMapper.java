package com.example.ecommerce.product.mapper;

import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity mapToProductEntity(ProductRequest productRequest);
}
