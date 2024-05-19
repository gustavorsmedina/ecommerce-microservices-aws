package tech.gustavomedina.email.dto;

import lombok.Builder;

@Builder
public record Email(
        String sender,
        String recipient,
        String subject,
        String body
    ) {
}
