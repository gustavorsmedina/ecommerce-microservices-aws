package tech.gustavomedina.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.gustavomedina.domains.enums.EventType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Event {

    private EventType eventType;
    private LocalDateTime createdAt;
    private Order payload;

}