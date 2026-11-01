package com.example.risk.service;

import com.example.risk.messaging.AlertEventProducer;
import com.example.risk.messaging.SecurityAlertEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AlertIngestionService {

    private final AlertEventProducer producer;

    public AlertIngestionService(AlertEventProducer producer) {
        this.producer = producer;
    }

    public void ingest(String alertId, String title, String category, String sourceHost, int baseScore) {
        SecurityAlertEvent event = new SecurityAlertEvent(
                alertId,
                title,
                category,
                sourceHost,
                baseScore,
                Instant.now()
        );
        producer.send(event);
    }
}
