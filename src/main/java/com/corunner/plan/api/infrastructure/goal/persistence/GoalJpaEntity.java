package com.corunner.plan.api.infrastructure.goal.persistence;

import com.corunner.plan.api.domain.goal.GoalType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "goals")
public class GoalJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false)
    private GoalType goalType;

    @Column(name = "goal_number", nullable = false, precision = 10, scale = 4)
    private BigDecimal goalNumber;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "weekly_workouts", nullable = false)
    private Integer weeklyWorkouts;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected GoalJpaEntity() {}

    public GoalJpaEntity(UUID id, GoalType goalType, BigDecimal goalNumber,
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

    public UUID getId() { return id; }
    public GoalType getGoalType() { return goalType; }
    public BigDecimal getGoalNumber() { return goalNumber; }
    public LocalDate getTargetDate() { return targetDate; }
    public Integer getWeeklyWorkouts() { return weeklyWorkouts; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
