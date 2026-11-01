package com.example.risk.service;

import com.example.risk.dto.CaseRequest;
import com.example.risk.dto.ScoreSummary;
import com.example.risk.model.Alert;
import com.example.risk.model.IncidentCase;
import com.example.risk.model.Policy;
import com.example.risk.repository.AlertRepository;
import com.example.risk.repository.IncidentCaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class CaseService {

    private final IncidentCaseRepository cases;
    private final AlertRepository alerts;
    private final RiskScoringService scoring;

    public CaseService(IncidentCaseRepository c, AlertRepository a, RiskScoringService s) {
        cases = c;
        alerts = a;
        scoring = s;
    }

    public List<IncidentCase> all() {
        return cases.findAll();
    }

    @Transactional
    public IncidentCase create(CaseRequest r) {
        return cases.save(new IncidentCase(r.name()));
    }

    @Transactional
    public IncidentCase add(long cid, long aid) {
        IncidentCase c = get(cid);
        Alert a = alerts.findById(aid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
        c.getAlerts().add(a);
        return c;
    }

    @Transactional
    public IncidentCase remove(long cid, long aid) {
        IncidentCase c = get(cid);
        c.getAlerts().removeIf(a -> a.getId().equals(aid));
        return c;
    }

    @Transactional
    public IncidentCase policies(long id, Set<Policy> p) {
        IncidentCase c = get(id);
        Set<Policy> req = p == null ? Set.of() : p;
        long n = req.stream()
                .filter(x -> x == Policy.CRITICAL_ASSET_BOOST || x == Policy.APT_CAMPAIGN_LINK)
                .count();
        if (n > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one escalation policy can be active");
        }
        c.getPolicies().clear();
        c.getPolicies().addAll(req);
        return c;
    }

    public ScoreSummary score(long id) {
        return scoring.score(get(id));
    }

    public IncidentCase get(long id) {
        return cases.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Case not found"));
    }
}
