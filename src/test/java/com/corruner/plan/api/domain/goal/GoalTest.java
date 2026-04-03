package com.corruner.plan.api.domain.goal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalTest {

    @Test
    void create_assignsAllFields() {
        GoalType goalType = GoalType.DISTANCE;
        BigDecimal goalNumber = new BigDecimal("42.5");
        LocalDate targetDate = LocalDate.of(2026, 12, 31);
        Integer weeklyWorkouts = 3;
        String description = "Marathon training";

        Goal goal = Goal.create(goalType, goalNumber, targetDate, weeklyWorkouts, description);

        assertThat(goal.getGoalType()).isEqualTo(goalType);
        assertThat(goal.getGoalNumber()).isEqualTo(goalNumber);
        assertThat(goal.getTargetDate()).isEqualTo(targetDate);
        assertThat(goal.getWeeklyWorkouts()).isEqualTo(weeklyWorkouts);
        assertThat(goal.getDescription()).isEqualTo(description);
    }

    @Test
    void create_generatesNonNullUUID() {
        Goal goal = Goal.create(GoalType.TIME, BigDecimal.ONE, LocalDate.now(), 1, null);

        assertThat(goal.getId()).isNotNull();
    }

    @Test
    void create_generatesUniqueUUIDs() {
        Goal goal1 = Goal.create(GoalType.TIME, BigDecimal.ONE, LocalDate.now(), 1, null);
        Goal goal2 = Goal.create(GoalType.TIME, BigDecimal.ONE, LocalDate.now(), 1, null);

        assertThat(goal1.getId()).isNotEqualTo(goal2.getId());
    }

    @Test
    void create_setsCreatedAtToNow() {
        Instant before = Instant.now();
        Goal goal = Goal.create(GoalType.PACE, BigDecimal.ONE, LocalDate.now(), 1, null);
        Instant after = Instant.now();

        assertThat(goal.getCreatedAt()).isAfterOrEqualTo(before);
        assertThat(goal.getCreatedAt()).isBeforeOrEqualTo(after);
    }

    @Test
    void create_allowsNullDescription() {
        Goal goal = Goal.create(GoalType.FREQUENCY, BigDecimal.TEN, LocalDate.now(), 5, null);

        assertThat(goal.getDescription()).isNull();
    }
}
