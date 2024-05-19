package tech.gustavomedina.order.mapper;

import org.springframework.stereotype.Component;
import tech.gustavomedina.domains.dto.Order;
import tech.gustavomedina.domains.dto.OrderProduct;
import tech.gustavomedina.order.dto.OrderProductRequest;
import tech.gustavomedina.order.dto.OrderProductResponse;
import tech.gustavomedina.order.dto.client.OrderProductStock;
import tech.gustavomedina.order.entity.OrderEntity;
import tech.gustavomedina.order.dto.OrderResponse;
import tech.gustavomedina.order.dto.OrderRequest;
import tech.gustavomedina.order.entity.OrderProductEntity;

@Component
public class OrderMapper {

    public OrderEntity toOrderEntity(OrderRequest orderRequest){

        var order = new OrderEntity();

        var products = orderRequest.products()
                .stream()
                .map(orderProduct -> toOrderProductEntity(orderProduct, order))
                .toList();

        order.setPaymentType(orderRequest.paymentType());
        order.setUserEmail(orderRequest.userEmail());
        order.setProducts(products);

        return order;
    }

    public OrderProductEntity toOrderProductEntity(OrderProductRequest orderProductRequest, OrderEntity orderEntity){

        return OrderProductEntity.builder()
                .id(null)
                .code(orderProductRequest.code())
                .name(orderProductRequest.name())
                .price(orderProductRequest.price())
                .quantity(orderProductRequest.quantity())
                .productOrder(orderEntity)
                .build();
    }

    public OrderResponse toOrderResponse(OrderEntity orderEntity){

        var products = orderEntity.getProducts().stream()
                .map(this::toOrderProductResponse)
                .toList();


        return OrderResponse.builder()
                .id(orderEntity.getId())
                .userEmail(orderEntity.getUserEmail())
                .createdAt(orderEntity.getCreatedAt())
                .updatedAt(orderEntity.getUpdatedAt())
                .orderStatus(orderEntity.getOrderStatus())
                .paymentType(orderEntity.getPaymentType())
                .paymentStatus(orderEntity.getPaymentStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .products(products)
                .build();

    }

    public OrderProductResponse toOrderProductResponse(OrderProductEntity orderProductEntity){

        return OrderProductResponse.builder()
                .code(orderProductEntity.getCode())
                .name(orderProductEntity.getName())
                .price(orderProductEntity.getPrice())
                .quantity(orderProductEntity.getQuantity())
                .build();

    }

    public OrderProductStock toOrderProductStock(OrderProductEntity orderProductEntity){

        return OrderProductStock.builder()
                .orderId(orderProductEntity.getId())
                .productCode(orderProductEntity.getCode())
                .quantity(orderProductEntity.getQuantity())
                .build();
    }

    public Order toOrder(OrderEntity orderEntity){

        return Order.builder()
                .id(orderEntity.getId())
                .userEmail(orderEntity.getUserEmail())
                .createdAt(orderEntity.getCreatedAt())
                .updatedAt(orderEntity.getUpdatedAt())
                .orderStatus(orderEntity.getOrderStatus())
                .paymentType(orderEntity.getPaymentType())
                .paymentStatus(orderEntity.getPaymentStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .products(orderEntity.getProducts().stream().map(this::toOrderProduct).toList())
                .build();
    }

    public OrderProduct toOrderProduct(OrderProductEntity orderProductEntity){

        return OrderProduct.builder()
                .id(orderProductEntity.getId())
                .code(orderProductEntity.getCode())
                .name(orderProductEntity.getName())
                .price(orderProductEntity.getPrice())
                .quantity(orderProductEntity.getQuantity())
                .build();
    }


}
