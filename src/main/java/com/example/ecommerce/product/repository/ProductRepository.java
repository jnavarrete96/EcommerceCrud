package com.example.ecommerce.product.repository;

import com.example.ecommerce.product.entity.ProductEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, ObjectId> {
    boolean existsByProductId(String productId);
    Optional<ProductEntity> findByProductId(String productId);
}
