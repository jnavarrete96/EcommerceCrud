package com.example.ecommerce.product.mapper;

import com.example.ecommerce.product.dto.ProductRequest;
import com.example.ecommerce.product.dto.ProductResponse;
import com.example.ecommerce.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity mapToProductEntity(ProductRequest productRequest);
    ProductResponse mapToProductResponse(ProductEntity productEntity);

    @Mapping(target = "productId", ignore = true)
    void updateProductFromDto(ProductRequest dto, @MappingTarget ProductEntity product);
}
