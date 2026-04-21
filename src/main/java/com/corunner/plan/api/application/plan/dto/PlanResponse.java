package com.corunner.plan.api.application.plan.dto;

import com.corunner.plan.api.domain.plan.Plan;

import java.time.Instant;
import java.util.UUID;

public record PlanResponse(
        UUID id,
        UUID goalId,
        String name,
        String description,
        Instant createdAt
) {
    public static PlanResponse from(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getGoalId(),
                plan.getName(),
                plan.getDescription(),
                plan.getCreatedAt()
        );
    }
}
