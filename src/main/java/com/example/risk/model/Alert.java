package com.example.risk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertCategory category;

    @Column(nullable = false)
    private String sourceHost;

    @Column(nullable = false)
    private int baseScore;

    protected Alert() {
    }

    public Alert(String title, AlertCategory category, String sourceHost, int baseScore) {
        this.title = title;
        this.category = category;
        this.sourceHost = sourceHost;
        this.baseScore = baseScore;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public AlertCategory getCategory() {
        return category;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public int getBaseScore() {
        return baseScore;
    }
}
