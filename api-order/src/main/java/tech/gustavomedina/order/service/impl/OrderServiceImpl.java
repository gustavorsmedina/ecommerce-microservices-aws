package tech.gustavomedina.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.enums.EventType;
import tech.gustavomedina.domains.enums.OrderStatus;
import tech.gustavomedina.domains.enums.PaymentStatus;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.order.entity.OrderEntity;
import tech.gustavomedina.order.exception.OrderNotFoundException;
import tech.gustavomedina.order.client.ProductClient;
import tech.gustavomedina.order.mapper.OrderMapper;
import tech.gustavomedina.order.dto.OrderResponse;
import tech.gustavomedina.order.dto.OrderRequest;
import tech.gustavomedina.order.producer.OrderProducer;
import tech.gustavomedina.order.repository.OrderRepository;
import tech.gustavomedina.order.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class  OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper;

    @CircuitBreaker(name = "save-order", fallbackMethod = "handleSaveOrderFallback")
    @Override
    public UUID save(OrderRequest orderRequest) throws JsonProcessingException {

        var order = createOrder(orderRequest);

        order.setOrderStatus(OrderStatus.PROCESSING);

        var orderSaved = orderRepository.save(order);

        var updateProductStock = orderSaved.getProducts()
                .stream()
                .map(orderMapper::toOrderProductStock)
                .toList();

        productClient.updateStockByOrder(updateProductStock);

        log.info("[{}] Order {} created at {}.", orderSaved.getOrderStatus(), orderSaved.getId(), LocalDateTime.now());

        orderProducer.sendEvent(objectMapper.writeValueAsString(createEvent(orderSaved)));

        return orderSaved.getId();
    }

    public UUID handleSaveOrderFallback(OrderRequest orderRequest, Exception ex) {

        var order = createOrder(orderRequest);

        order.setOrderStatus(OrderStatus.COMMUNICATION_FAILED);

        var orderSaved = orderRepository.save(order);

        log.info("[{}] Order {} created at {}.", orderSaved.getOrderStatus(), orderSaved.getId(), LocalDateTime.now());

        return orderSaved.getId();
    }

    public OrderEntity createOrder(OrderRequest orderRequest){
        var order = orderMapper.toOrderEntity(orderRequest);

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setPaymentStatus(PaymentStatus.PENDING);

        order.setTotalPrice(order.getProducts()
                .stream()
                .map(o -> o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return order;
    }

    public Event createEvent(OrderEntity orderEntity) {

        var order = orderMapper.toOrder(orderEntity);

        return new Event(
                EventType.ORDER_PLACED,
                LocalDateTime.now(),
                order
        );
    }

    @Override
    public List<OrderResponse> findAll() {
        var orders = orderRepository.findAll();

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(UUID id) {

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found."));

        var products = order.getProducts().stream()
                .map(orderMapper::toOrderProductResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .userEmail(order.getUserEmail())
                .orderStatus(order.getOrderStatus())
                .paymentType(order.getPaymentType())
                .paymentStatus(order.getPaymentStatus())
                .totalPrice(order.getTotalPrice())
                .products(products)
                .build();
    }
}
