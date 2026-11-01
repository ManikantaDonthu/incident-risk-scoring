package com.example.risk.service;

import com.example.risk.dto.ScoreSummary;
import com.example.risk.model.Alert;
import com.example.risk.model.AlertCategory;
import com.example.risk.model.IncidentCase;
import com.example.risk.model.Policy;
import com.example.risk.model.Severity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RiskScoringService {

    public ScoreSummary score(IncidentCase c) {
        Set<Alert> a = c.getAlerts();
        int raw = a.stream().mapToInt(Alert::getBaseScore).sum();
        int score = raw;
        List<String> adj = new ArrayList<>();
        Set<Policy> p = c.getPolicies();

        if (p.contains(Policy.REPEAT_NOISE_DAMPING)) {
            int sum = a.stream()
                    .filter(x -> x.getCategory() == AlertCategory.network_scan)
                    .mapToInt(Alert::getBaseScore)
                    .sum();
            int max = a.stream()
                    .filter(x -> x.getCategory() == AlertCategory.network_scan)
                    .mapToInt(Alert::getBaseScore)
                    .max()
                    .orElse(0);
            int reduction = sum - max;
            score -= reduction;
            if (reduction > 0) {
                adj.add("REPEAT_NOISE_DAMPING: -" + reduction);
            }
        }

        int before = score;
        if (p.contains(Policy.CRITICAL_ASSET_BOOST)) {
            int boost = Math.min((int) Math.floor(before * .20), 30);
            score += boost;
            adj.add("CRITICAL_ASSET_BOOST: +" + boost);
        } else if (p.contains(Policy.APT_CAMPAIGN_LINK) && before >= 50) {
            score += 25;
            adj.add("APT_CAMPAIGN_LINK: +25");
        }

        int fin = Math.min(100, Math.max(0, score));
        if (score > 100) {
            adj.add("Final score capped at 100");
        }

        Severity sev = fin <= 29 ? Severity.LOW
                : fin <= 59 ? Severity.MEDIUM
                : fin <= 84 ? Severity.HIGH
                : Severity.CRITICAL;

        return new ScoreSummary(
                raw,
                adj,
                fin,
                sev,
                p.stream().map(Enum::name).sorted().toList()
        );
    }
}
