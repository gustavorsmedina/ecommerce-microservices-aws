package tech.gustavomedina.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder

public class OrderProduct {

    private UUID id;
    private String code;
    private String name;
    private BigDecimal price;
    private Integer quantity;

}
