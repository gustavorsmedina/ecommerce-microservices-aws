package tech.gustavomedina.email.service;

import tech.gustavomedina.domains.dto.Event;

public interface EmailService {

    public void sendEmail(Event event, String message, String body);

}
