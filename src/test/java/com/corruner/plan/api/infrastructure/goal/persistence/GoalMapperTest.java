package com.corruner.plan.api.infrastructure.goal.persistence;

import com.corruner.plan.api.domain.goal.Goal;
import com.corruner.plan.api.domain.goal.GoalType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalMapperTest {

    private final GoalMapper mapper = new GoalMapper();

    @Test
    void toJpaEntity_mapsAllFields() {
        UUID id = UUID.randomUUID();
        GoalType goalType = GoalType.DISTANCE;
        BigDecimal goalNumber = new BigDecimal("42.5");
        LocalDate targetDate = LocalDate.of(2026, 12, 31);
        Integer weeklyWorkouts = 3;
        String description = "Marathon training";
        Instant createdAt = Instant.now();

        Goal goal = new Goal(id, goalType, goalNumber, targetDate, weeklyWorkouts, description, createdAt);

        GoalJpaEntity entity = mapper.toJpaEntity(goal);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getGoalType()).isEqualTo(goalType);
        assertThat(entity.getGoalNumber()).isEqualTo(goalNumber);
        assertThat(entity.getTargetDate()).isEqualTo(targetDate);
        assertThat(entity.getWeeklyWorkouts()).isEqualTo(weeklyWorkouts);
        assertThat(entity.getDescription()).isEqualTo(description);
        assertThat(entity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toJpaEntity_preservesNullDescription() {
        Goal goal = new Goal(UUID.randomUUID(), GoalType.TIME, BigDecimal.ONE,
                LocalDate.now(), 1, null, Instant.now());

        GoalJpaEntity entity = mapper.toJpaEntity(goal);

        assertThat(entity.getDescription()).isNull();
    }

    @Test
    void toDomain_mapsAllFields() {
        UUID id = UUID.randomUUID();
        GoalType goalType = GoalType.PACE;
        BigDecimal goalNumber = new BigDecimal("5.5");
        LocalDate targetDate = LocalDate.of(2026, 6, 30);
        Integer weeklyWorkouts = 5;
        String description = "Pace goal";
        Instant createdAt = Instant.now();

        GoalJpaEntity entity = new GoalJpaEntity(id, goalType, goalNumber, targetDate,
                weeklyWorkouts, description, createdAt);

        Goal goal = mapper.toDomain(entity);

        assertThat(goal.getId()).isEqualTo(id);
        assertThat(goal.getGoalType()).isEqualTo(goalType);
        assertThat(goal.getGoalNumber()).isEqualTo(goalNumber);
        assertThat(goal.getTargetDate()).isEqualTo(targetDate);
        assertThat(goal.getWeeklyWorkouts()).isEqualTo(weeklyWorkouts);
        assertThat(goal.getDescription()).isEqualTo(description);
        assertThat(goal.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toDomain_preservesNullDescription() {
        GoalJpaEntity entity = new GoalJpaEntity(UUID.randomUUID(), GoalType.FREQUENCY,
                BigDecimal.TEN, LocalDate.now(), 4, null, Instant.now());

        Goal goal = mapper.toDomain(entity);

        assertThat(goal.getDescription()).isNull();
    }
}
