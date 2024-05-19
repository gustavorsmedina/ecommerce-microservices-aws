package tech.gustavomedina.email.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.email.dto.Email;
import tech.gustavomedina.email.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(Event event, String subject, String body) {

        var recipient = event.getPayload().getUserEmail();

        var email = Email.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(subject)
                .body(body)
                .build();

        var message = new SimpleMailMessage();

        message.setTo(email.recipient());
        message.setSubject(email.subject());
        message.setText(email.body());

        mailSender.send(message);

        log.info("Sending a {} email to {}.", event.getEventType(), recipient);
    }

}
