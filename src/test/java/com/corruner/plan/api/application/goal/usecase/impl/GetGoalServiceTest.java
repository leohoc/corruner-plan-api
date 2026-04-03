package com.corruner.plan.api.application.goal.usecase.impl;

import com.corruner.plan.api.application.goal.dto.GoalResponse;
import com.corruner.plan.api.application.goal.port.GoalRepository;
import com.corruner.plan.api.controller.goal.exception.GoalNotFoundException;
import com.corruner.plan.api.domain.goal.Goal;
import com.corruner.plan.api.domain.goal.GoalType;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GetGoalService getGoalService;

    @Test
    void execute_returnsGoalResponse_whenFound() {
        UUID id = UUID.randomUUID();
        Goal goal = new Goal(id, GoalType.DISTANCE, new BigDecimal("42.5"),
                LocalDate.of(2026, 12, 31), 3, "Marathon", Instant.now());
        when(goalRepository.findById(id)).thenReturn(Optional.of(goal));

        GoalResponse response = getGoalService.execute(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.goalType()).isEqualTo(goal.getGoalType());
        assertThat(response.goalNumber()).isEqualTo(goal.getGoalNumber());
        assertThat(response.targetDate()).isEqualTo(goal.getTargetDate());
        assertThat(response.weeklyWorkouts()).isEqualTo(goal.getWeeklyWorkouts());
        assertThat(response.description()).isEqualTo(goal.getDescription());
        assertThat(response.createdAt()).isEqualTo(goal.getCreatedAt());
    }

    @Test
    void execute_throwsGoalNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(goalRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getGoalService.execute(id))
                .isInstanceOf(GoalNotFoundException.class);
    }

    @Test
    void execute_exceptionMessage_containsRequestedId() {
        UUID id = UUID.randomUUID();
        when(goalRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getGoalService.execute(id))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
