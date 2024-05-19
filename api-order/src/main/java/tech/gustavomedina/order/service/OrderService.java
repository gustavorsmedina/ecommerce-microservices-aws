package tech.gustavomedina.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tech.gustavomedina.order.dto.OrderResponse;
import tech.gustavomedina.order.dto.OrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID save(OrderRequest orderRequest) throws JsonProcessingException;
    List<OrderResponse> findAll();
    OrderResponse findById(UUID id);

}
