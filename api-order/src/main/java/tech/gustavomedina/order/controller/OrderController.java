package tech.gustavomedina.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.gustavomedina.order.dto.OrderRequest;
import tech.gustavomedina.order.dto.OrderResponse;

import java.util.List;

public interface OrderController {

    @PostMapping
    ResponseEntity<Void> save(@RequestBody @Valid OrderRequest orderRequest) throws JsonProcessingException;
    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable("id") String id);

}
