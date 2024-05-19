package tech.gustavomedina.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.gustavomedina.product.entity.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
}
