package tech.gustavomedina.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.gustavomedina.domains.enums.OrderStatus;
import tech.gustavomedina.domains.enums.PaymentStatus;
import tech.gustavomedina.domains.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Order {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userEmail;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private BigDecimal totalPrice;
    private List<OrderProduct> products;

}
