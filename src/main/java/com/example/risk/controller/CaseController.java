package com.example.risk.controller;

import com.example.risk.dto.CaseRequest;
import com.example.risk.dto.PolicyRequest;
import com.example.risk.dto.ScoreSummary;
import com.example.risk.model.IncidentCase;
import com.example.risk.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService s;

    public CaseController(CaseService s) {
        this.s = s;
    }

    @GetMapping
    public List<IncidentCase> list() {
        return s.all();
    }

    @PostMapping
    public IncidentCase create(@Valid @RequestBody CaseRequest r) {
        return s.create(r);
    }

    @GetMapping("/{id}")
    public IncidentCase get(@PathVariable long id) {
        return s.get(id);
    }

    @PostMapping("/{caseId}/alerts/{alertId}")
    public IncidentCase add(@PathVariable long caseId, @PathVariable long alertId) {
        return s.add(caseId, alertId);
    }

    @DeleteMapping("/{caseId}/alerts/{alertId}")
    public IncidentCase remove(@PathVariable long caseId, @PathVariable long alertId) {
        return s.remove(caseId, alertId);
    }

    @PutMapping("/{id}/policies")
    public IncidentCase policies(@PathVariable long id, @RequestBody PolicyRequest r) {
        return s.policies(id, r.policies());
    }

    @GetMapping("/{id}/score")
    public ScoreSummary score(@PathVariable long id) {
        return s.score(id);
    }
}
