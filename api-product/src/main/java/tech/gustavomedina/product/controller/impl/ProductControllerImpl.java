package tech.gustavomedina.product.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gustavomedina.product.controller.ProductController;
import tech.gustavomedina.product.dto.ProductRequest;
import tech.gustavomedina.product.dto.ProductResponse;
import tech.gustavomedina.product.dto.client.OrderProductStock;
import tech.gustavomedina.product.service.impl.ProductServiceImpl;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductControllerImpl implements ProductController {

    private final ProductServiceImpl productService;

    @Override
    public ResponseEntity<Void> save(ProductRequest productRequest) {

        var productId = productService.save(productRequest);

        return ResponseEntity.created(URI.create("/v1/products/" + productId)).build();
    }

    @Override
    public ResponseEntity<List<ProductResponse>> findAll() {

        var products = productService.findAll();

        return ResponseEntity.ok().body(products);
    }

    @Override
    public ResponseEntity<ProductResponse> findById(String id) {

        var product = productService.findById(id);

        return ResponseEntity.ok().body(product);
    }

    @Override
    public ResponseEntity<Void> updateById(String id, ProductRequest productRequest) {

        productService.updateById(id, productRequest);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String id) {

        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateStockByOrder(List<OrderProductStock> orderProductStock) {

        productService.updateStockByOrder(orderProductStock);

        return ResponseEntity.ok().build();
    }
}
