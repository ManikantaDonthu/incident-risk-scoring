package com.example.risk.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "incident_cases")
public class IncidentCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "case_alerts",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "alert_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"case_id", "alert_id"})
    )
    private Set<Alert> alerts = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "case_policies", joinColumns = @JoinColumn(name = "case_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "policy")
    private Set<Policy> policies = new LinkedHashSet<>();

    protected IncidentCase() {
    }

    public IncidentCase(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }
}
