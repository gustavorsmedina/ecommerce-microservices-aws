package tech.gustavomedina.product.mapper;

import org.springframework.stereotype.Component;
import tech.gustavomedina.product.entity.ProductEntity;
import tech.gustavomedina.product.dto.ProductRequest;
import tech.gustavomedina.product.dto.ProductResponse;

@Component
public class ProductMapper {

    public ProductEntity toProduct(ProductRequest productRequest){

        return ProductEntity.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .stock(productRequest.stock())
                .build();
    }

    public ProductResponse toProductResponse(ProductEntity productEntity){

        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .stock(productEntity.getStock())
                .build();
    }

}
