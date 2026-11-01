package com.example.risk.repository;

import com.example.risk.model.IncidentCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentCaseRepository extends JpaRepository<IncidentCase, Long> {
}
