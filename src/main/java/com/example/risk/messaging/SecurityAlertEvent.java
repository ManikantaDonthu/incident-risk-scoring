package com.example.risk.messaging;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

public class SecurityAlertEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String alertId;
    private String title;
    private String category;
    private String sourceHost;
    private int baseScore;
    private Instant occurredAt;

    public SecurityAlertEvent() {
    }

    public SecurityAlertEvent(String alertId, String title, String category, String sourceHost, int baseScore, Instant occurredAt) {
        this.alertId = alertId;
        this.title = title;
        this.category = category;
        this.sourceHost = sourceHost;
        this.baseScore = baseScore;
        this.occurredAt = occurredAt;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(String sourceHost) {
        this.sourceHost = sourceHost;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
