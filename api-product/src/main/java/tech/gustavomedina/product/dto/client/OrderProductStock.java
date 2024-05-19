package tech.gustavomedina.product.dto.client;

import java.util.UUID;

public record OrderProductStock(
        UUID orderId,
        String productCode,
        Integer quantity
    ) {
}
