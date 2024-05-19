package tech.gustavomedina.order.producer;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    @Value("${sns.topic.order}")
    private String topic;

    private final SnsTemplate snsTemplate;

    public void sendEvent(String payload){

        snsTemplate.sendNotification(topic, SnsNotification.of(payload));

        log.info("Sending event to topic {} with data {}", topic, payload);
    }

}
