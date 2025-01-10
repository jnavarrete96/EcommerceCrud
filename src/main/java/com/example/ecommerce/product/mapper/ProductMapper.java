package com.example.ecommerce.product.mapper;

import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity mapToProductEntity(ProductRequest productRequest);
}
