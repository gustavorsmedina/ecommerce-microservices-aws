package tech.gustavomedina.order.dto;

import java.math.BigDecimal;

public record OrderProductRequest(
        String code,
        String name,
        BigDecimal price,
        Integer quantity
    ) {
}
