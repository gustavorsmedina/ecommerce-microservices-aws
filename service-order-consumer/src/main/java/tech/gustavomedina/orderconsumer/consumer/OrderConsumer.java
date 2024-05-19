package tech.gustavomedina.orderconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.orderconsumer.service.OrderService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Value("${sqs.queue.payment}")
    private String paymentQueue;

    @SqsListener("${sqs.queue.payment}")
    public void consume(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", paymentQueue, payload);

        var event = objectMapper.readValue(payload, Event.class);

        orderService.updateOrder(event);
    }

}