package com.example.risk.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AlertEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AlertEventConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic:security-alerts}", groupId = "${spring.kafka.consumer.group-id:incident-risk-group}")
    public void consume(SecurityAlertEvent event) {
        log.info("Received security alert event from {}: {} [{}]", event.getSourceHost(), event.getTitle(), event.getCategory());
    }
}
