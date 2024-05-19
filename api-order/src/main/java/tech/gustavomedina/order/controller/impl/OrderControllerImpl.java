package tech.gustavomedina.order.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gustavomedina.order.controller.OrderController;
import tech.gustavomedina.order.dto.OrderRequest;
import tech.gustavomedina.order.dto.OrderResponse;
import tech.gustavomedina.order.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> save(OrderRequest orderRequest) throws JsonProcessingException {
        var orderId = orderService.save(orderRequest);

        return ResponseEntity.created(URI.create("/v1/products/" + orderId)).build();
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        var orders = orderService.findAll();

        return ResponseEntity.ok().body(orders);
    }

    @Override
    public ResponseEntity<OrderResponse> findById(String id) {
        var order = orderService.findById(UUID.fromString(id));

        return ResponseEntity.ok().body(order);
    }
}
