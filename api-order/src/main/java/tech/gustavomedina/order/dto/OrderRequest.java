package tech.gustavomedina.order.dto;


import tech.gustavomedina.domains.enums.PaymentType;

import java.util.List;

public record OrderRequest(
        PaymentType paymentType,
        String userEmail,
        List<OrderProductRequest> products
    ) {
}
