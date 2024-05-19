package tech.gustavomedina.product.service;

import tech.gustavomedina.product.dto.ProductRequest;
import tech.gustavomedina.product.dto.ProductResponse;
import tech.gustavomedina.product.dto.client.OrderProductStock;

import java.util.List;

public interface ProductService {

    String save(ProductRequest productRequest);
    List<ProductResponse> findAll();
    ProductResponse findById(String id);
    void updateById(String id, ProductRequest productRequest);
    void deleteById(String id);
    void updateStockByOrder(List<OrderProductStock> orderProductStock);


}
