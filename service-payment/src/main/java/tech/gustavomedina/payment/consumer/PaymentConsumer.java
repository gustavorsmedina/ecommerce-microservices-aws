package tech.gustavomedina.payment.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Value("${sqs.queue.order}")
    private String orderQueue;

    @SqsListener("${sqs.queue.order}")
    public void consume(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", orderQueue, payload);

        var event = objectMapper.readValue(payload, Event.class);

        paymentService.processPayment(event);
    }

}
