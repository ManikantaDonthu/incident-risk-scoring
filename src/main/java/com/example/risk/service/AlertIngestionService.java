package com.example.risk.service;

import com.example.risk.messaging.AlertEventProducer;
import com.example.risk.messaging.SecurityAlertEvent;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AlertIngestionService {

    private final ObjectProvider<AlertEventProducer> producerProvider;

    public AlertIngestionService(ObjectProvider<AlertEventProducer> producerProvider) {
        this.producerProvider = producerProvider;
    }

    public void ingest(String alertId, String title, String category, String sourceHost, int baseScore) {
        AlertEventProducer producer = producerProvider.getIfAvailable();
        if (producer == null) {
            return;
        }

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
