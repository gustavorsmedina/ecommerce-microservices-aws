package tech.gustavomedina.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.domains.enums.EventType;
import tech.gustavomedina.domains.enums.PaymentStatus;
import tech.gustavomedina.domains.enums.PaymentType;
import tech.gustavomedina.payment.exception.InvalidTotalPrice;
import tech.gustavomedina.payment.exception.InvalidPaymentType;
import tech.gustavomedina.payment.producer.PaymentProducer;
import tech.gustavomedina.payment.service.PaymentService;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ObjectMapper objectMapper;
    private final PaymentProducer paymentProducer;

    @Override
    public void processPayment(Event event) throws JsonProcessingException {

        try {
            validatePaymentType(event);
            validateTotalPrice(event);
            event.getPayload().setPaymentStatus(PaymentStatus.APPROVED);
        } catch (Exception ex) {
            event.getPayload().setPaymentStatus(PaymentStatus.RECUSED);
        } finally {
            event.setEventType(EventType.PAYMENT_PROCESSED);
            paymentProducer.sendEvent(objectMapper.writeValueAsString(event));
        }

    }

    public void validatePaymentType(Event event) {
        if (!Arrays.asList(PaymentType.values()).contains(event.getPayload().getPaymentType())) {
            throw new InvalidPaymentType("Invalid payment type.");
        }
    }

    public void validateTotalPrice(Event event) {
        if (event.getPayload().getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTotalPrice("Price must be greater than zero.");
        }
    }


}
