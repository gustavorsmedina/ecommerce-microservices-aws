package tech.gustavomedina.orderconsumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.domains.enums.OrderStatus;
import tech.gustavomedina.orderconsumer.repository.OrderRepository;
import tech.gustavomedina.orderconsumer.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void updateOrder(Event event){

        var order = orderRepository.findById(event.getPayload().getId()).get();

        order.setOrderStatus(OrderStatus.DONE);
        order.setPaymentStatus(event.getPayload().getPaymentStatus());

        orderRepository.save(order);

    }
}
