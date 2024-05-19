package tech.gustavomedina.orderconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tech.gustavomedina.domains.dto.Event;

public interface OrderService {

    public void updateOrder(Event event) throws JsonProcessingException;

}
