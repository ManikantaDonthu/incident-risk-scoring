package com.example.risk.service;

import com.example.risk.model.Alert;
import com.example.risk.model.AlertCategory;
import com.example.risk.model.IncidentCase;
import com.example.risk.model.Policy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskScoringServiceTest {

    RiskScoringService s = new RiskScoringService();

    @Test
    void noiseDampingOnlyCountsLargestNetworkScan() {
        IncidentCase c = new IncidentCase("x");
        c.getAlerts().add(new Alert("n1", AlertCategory.network_scan, "h", 12));
        c.getAlerts().add(new Alert("n2", AlertCategory.network_scan, "h", 20));
        c.getAlerts().add(new Alert("p", AlertCategory.phishing, "h", 10));
        c.getPolicies().add(Policy.REPEAT_NOISE_DAMPING);

        var result = s.score(c);

        assertEquals(42, result.rawScore());
        assertEquals(30, result.finalScore());
    }

    @Test
    void aptRequiresThreshold() {
        IncidentCase c = new IncidentCase("x");
        c.getAlerts().add(new Alert("a", AlertCategory.malware, "h", 50));
        c.getPolicies().add(Policy.APT_CAMPAIGN_LINK);

        assertEquals(75, s.score(c).finalScore());
    }

    @Test
    void boostRoundsDown() {
        IncidentCase c = new IncidentCase("x");
        c.getAlerts().add(new Alert("a", AlertCategory.malware, "h", 39));
        c.getAlerts().add(new Alert("b", AlertCategory.phishing, "h", 20));
        c.getPolicies().add(Policy.CRITICAL_ASSET_BOOST);

        assertEquals(70, s.score(c).finalScore());
    }
}
