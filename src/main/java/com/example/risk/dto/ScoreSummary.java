package com.example.risk.dto;

import com.example.risk.model.Severity;

import java.util.List;

public record ScoreSummary(
        int rawScore,
        List<String> adjustments,
        int finalScore,
        Severity severity,
        List<String> appliedPolicies
) {
}
