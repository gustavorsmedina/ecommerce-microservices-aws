package tech.gustavomedina.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.gustavomedina.product.dto.client.OrderProductStock;
import tech.gustavomedina.product.exception.ProductNotFoundException;
import tech.gustavomedina.product.mapper.ProductMapper;
import tech.gustavomedina.product.dto.ProductRequest;
import tech.gustavomedina.product.dto.ProductResponse;
import tech.gustavomedina.product.repository.ProductRepository;
import tech.gustavomedina.product.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public String save(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);

        var productSaved = productRepository.save(product);

        return productSaved.getId();
    }

    @Override
    public List<ProductResponse> findAll() {

        var products = productRepository.findAll();

        return products.stream().map(productMapper::toProductResponse).toList();
    }

    @Override
    public ProductResponse findById(String id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock()
        );
    }

    @Override
    public void updateById(String id, ProductRequest productRequest) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());

        productRepository.save(product);
    }

    @Override
    public void deleteById(String id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        productRepository.delete(product);
    }

    @Override
    public void updateStockByOrder(List<OrderProductStock> orderProductStock) {

        orderProductStock.forEach(stockRequest -> {
            var product = productRepository.findById(stockRequest.productCode())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found."));

            product.setStock(product.getStock() - stockRequest.quantity());

            var productSaved = productRepository.save(product);

            log.info("Order {} updated the stock of {} to {}.", stockRequest.orderId(), product.getName(), productSaved.getStock());
        });

    }

}
