package com.example.risk.dto;

import jakarta.validation.constraints.NotBlank;

public record CaseRequest(@NotBlank String name) {
}
