package com.example.risk.dto;

import com.example.risk.model.Policy;

import java.util.Set;

public record PolicyRequest(Set<Policy> policies) {
}
