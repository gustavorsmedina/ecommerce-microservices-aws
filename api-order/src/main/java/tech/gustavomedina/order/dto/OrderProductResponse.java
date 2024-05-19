package tech.gustavomedina.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderProductResponse(
        String code,
        String name,
        BigDecimal price,
        Integer quantity
    ) {
}
