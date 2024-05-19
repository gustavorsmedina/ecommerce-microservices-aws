package tech.gustavomedina.payment.producer;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    @Value("${sns.topic.payment}")
    private String paymentTopic;

    private final SnsTemplate snsTemplate;

    public void sendEvent(String payload) {

        snsTemplate.sendNotification(paymentTopic, SnsNotification.of(payload));

        log.info("Sending event to topic {} with data {}", paymentTopic, payload);
    }

}
