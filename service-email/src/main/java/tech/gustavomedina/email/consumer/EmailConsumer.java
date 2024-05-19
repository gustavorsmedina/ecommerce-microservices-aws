package tech.gustavomedina.email.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.email.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Value("${sqs.queue.payment}")
    private String paymentQueue;

    @Value("${sqs.queue.order}")
    private String orderQueue;

    @SqsListener("${sqs.queue.payment}")
    public void consumePayment(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", paymentQueue, payload);

        var event = objectMapper.readValue(payload, Event.class);

        var subject = "New payment " + event.getPayload().getId();
        var body = event.getPayload().getPaymentStatus().toString();

        emailService.sendEmail(event, subject, body);
    }

    @SqsListener("${sqs.queue.order}")
    public void consumeOrder(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", orderQueue, payload);

        var event = objectMapper.readValue(payload, Event.class);

        var subject = "New order " + event.getPayload().getId();
        var body = objectMapper.writeValueAsString(event.getPayload().getProducts());

        emailService.sendEmail(event, subject, body);
    }

}
