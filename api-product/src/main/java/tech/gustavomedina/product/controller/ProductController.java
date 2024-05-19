package tech.gustavomedina.product.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gustavomedina.product.dto.client.OrderProductStock;
import tech.gustavomedina.product.dto.ProductRequest;
import tech.gustavomedina.product.dto.ProductResponse;

import java.util.List;

public interface ProductController {

    @PostMapping
    ResponseEntity<Void> save(@RequestBody @Valid ProductRequest productRequest);
    @GetMapping
    ResponseEntity<List<ProductResponse>> findAll();
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> findById(@PathVariable("id") String id);
    @PutMapping("/{id}")
    ResponseEntity<Void> updateById(@PathVariable("id") String id, @RequestBody @Valid ProductRequest productRequest);
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable("id") String id);

    @PutMapping("/stock")
    ResponseEntity<Void> updateStockByOrder(@RequestBody List<OrderProductStock> orderProductStock) throws Exception;

}
