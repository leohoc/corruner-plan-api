package com.corruner.plan.api.application.goal.dto;

import com.corruner.plan.api.domain.goal.Goal;
import com.corruner.plan.api.domain.goal.GoalType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalResponseTest {

    @Test
    void from_mapsAllFieldsFromGoal() {
        UUID id = UUID.randomUUID();
        GoalType goalType = GoalType.DISTANCE;
        BigDecimal goalNumber = new BigDecimal("42.5");
        LocalDate targetDate = LocalDate.of(2026, 12, 31);
        Integer weeklyWorkouts = 3;
        String description = "Marathon training";
        Instant createdAt = Instant.now();

        Goal goal = new Goal(id, goalType, goalNumber, targetDate, weeklyWorkouts, description, createdAt);

        GoalResponse response = GoalResponse.from(goal);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.goalType()).isEqualTo(goalType);
        assertThat(response.goalNumber()).isEqualTo(goalNumber);
        assertThat(response.targetDate()).isEqualTo(targetDate);
        assertThat(response.weeklyWorkouts()).isEqualTo(weeklyWorkouts);
        assertThat(response.description()).isEqualTo(description);
        assertThat(response.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void from_preservesNullDescription() {
        Goal goal = new Goal(UUID.randomUUID(), GoalType.TIME, BigDecimal.ONE,
                LocalDate.now(), 1, null, Instant.now());

        GoalResponse response = GoalResponse.from(goal);

        assertThat(response.description()).isNull();
    }
}
