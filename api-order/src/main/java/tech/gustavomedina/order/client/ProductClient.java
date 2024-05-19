package tech.gustavomedina.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.gustavomedina.order.dto.client.OrderProductStock;

import java.util.List;

@FeignClient(url = "http://localhost:8080", value = "service-product")
public interface ProductClient {

    @PutMapping("/v1/products/stock")
    ResponseEntity<Void> updateStockByOrder(@RequestBody List<OrderProductStock> orderProductStock);

}
