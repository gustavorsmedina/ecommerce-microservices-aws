package tech.gustavomedina.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tech.gustavomedina.domains.dto.Event;

public interface PaymentService {

    public void processPayment(Event event) throws JsonProcessingException;

}
