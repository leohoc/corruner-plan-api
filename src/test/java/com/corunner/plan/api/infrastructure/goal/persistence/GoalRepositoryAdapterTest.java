package com.corunner.plan.api.infrastructure.goal.persistence;

import com.corunner.plan.api.domain.goal.Goal;
import com.corunner.plan.api.domain.goal.GoalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalRepositoryAdapterTest {

    @Mock
    private GoalJpaRepository jpaRepository;

    @Mock
    private GoalMapper mapper;

    @InjectMocks
    private GoalRepositoryAdapter adapter;

    @Test
    void save_mapsToJpaEntity_savesAndMapsBack() {
        Goal goal = new Goal(UUID.randomUUID(), GoalType.DISTANCE, new BigDecimal("42.5"),
                LocalDate.of(2026, 12, 31), 3, "Marathon", Instant.now());
        GoalJpaEntity entity = new GoalJpaEntity(goal.getId(), goal.getGoalType(), goal.getGoalNumber(),
                goal.getTargetDate(), goal.getWeeklyWorkouts(), goal.getDescription(), goal.getCreatedAt());

        when(mapper.toJpaEntity(goal)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(goal);

        Goal result = adapter.save(goal);

        verify(mapper).toJpaEntity(goal);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(entity);
        assertThat(result).isEqualTo(goal);
    }

    @Test
    void findById_returnsMappedGoal_whenPresent() {
        UUID id = UUID.randomUUID();
        GoalJpaEntity entity = new GoalJpaEntity(id, GoalType.TIME, BigDecimal.ONE,
                LocalDate.now(), 1, null, Instant.now());
        Goal goal = new Goal(id, GoalType.TIME, BigDecimal.ONE, LocalDate.now(), 1, null, Instant.now());

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(goal);

        Optional<Goal> result = adapter.findById(id);

        assertThat(result).isPresent().contains(goal);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findById_returnsEmpty_whenNotPresent() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Goal> result = adapter.findById(id);

        assertThat(result).isEmpty();
    }
}
