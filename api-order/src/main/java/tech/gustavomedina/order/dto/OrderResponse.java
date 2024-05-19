package tech.gustavomedina.order.dto;

import lombok.Builder;
import tech.gustavomedina.domains.enums.OrderStatus;
import tech.gustavomedina.domains.enums.PaymentStatus;
import tech.gustavomedina.domains.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID id,
        String userEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus orderStatus,
        PaymentType paymentType,
        PaymentStatus paymentStatus,
        BigDecimal totalPrice,
        List<OrderProductResponse> products
    ) {
}
