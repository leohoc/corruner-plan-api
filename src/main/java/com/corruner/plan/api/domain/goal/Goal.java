package com.corruner.plan.api.domain.goal;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Goal {

    private final UUID id;
    private final GoalType goalType;
    private final BigDecimal goalNumber;
    private final LocalDate targetDate;
    private final Integer weeklyWorkouts;
    private final String description;
    private final Instant createdAt;

    public Goal(UUID id, GoalType goalType, BigDecimal goalNumber,
                LocalDate targetDate, Integer weeklyWorkouts,
                String description, Instant createdAt) {
        this.id = id;
        this.goalType = goalType;
        this.goalNumber = goalNumber;
        this.targetDate = targetDate;
        this.weeklyWorkouts = weeklyWorkouts;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Goal create(GoalType goalType, BigDecimal goalNumber,
                              LocalDate targetDate, Integer weeklyWorkouts,
                              String description) {
        return new Goal(
                UUID.randomUUID(),
                goalType,
                goalNumber,
                targetDate,
                weeklyWorkouts,
                description,
                Instant.now()
        );
    }

    public UUID getId() { return id; }
    public GoalType getGoalType() { return goalType; }
    public BigDecimal getGoalNumber() { return goalNumber; }
    public LocalDate getTargetDate() { return targetDate; }
    public Integer getWeeklyWorkouts() { return weeklyWorkouts; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
