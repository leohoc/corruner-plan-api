package com.corruner.plan.api.application.goal.dto;

import com.corruner.plan.api.domain.goal.Goal;
import com.corruner.plan.api.domain.goal.GoalType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record GoalResponse(
        UUID id,
        GoalType goalType,
        BigDecimal goalNumber,
        LocalDate targetDate,
        Integer weeklyWorkouts,
        String description,
        Instant createdAt
) {
    public static GoalResponse from(Goal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getGoalType(),
                goal.getGoalNumber(),
                goal.getTargetDate(),
                goal.getWeeklyWorkouts(),
                goal.getDescription(),
                goal.getCreatedAt()
        );
    }
}
