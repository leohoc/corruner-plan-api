package com.corruner.plan.api.application.goal.usecase.impl;

import com.corruner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corruner.plan.api.application.goal.dto.GoalResponse;
import com.corruner.plan.api.application.goal.port.GoalRepository;
import com.corruner.plan.api.domain.goal.Goal;
import com.corruner.plan.api.domain.goal.GoalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private CreateGoalService createGoalService;

    @Test
    void execute_savesGoalWithRequestFieldsAndReturnsResponse() {
        CreateGoalRequest request = new CreateGoalRequest(
                GoalType.DISTANCE,
                new BigDecimal("42.5"),
                LocalDate.of(2026, 12, 31),
                3,
                "Marathon training"
        );
        when(goalRepository.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

        GoalResponse response = createGoalService.execute(request);

        ArgumentCaptor<Goal> goalCaptor = ArgumentCaptor.forClass(Goal.class);
        verify(goalRepository).save(goalCaptor.capture());
        Goal savedGoal = goalCaptor.getValue();

        assertThat(savedGoal.getGoalType()).isEqualTo(request.goalType());
        assertThat(savedGoal.getGoalNumber()).isEqualTo(request.goalNumber());
        assertThat(savedGoal.getTargetDate()).isEqualTo(request.targetDate());
        assertThat(savedGoal.getWeeklyWorkouts()).isEqualTo(request.weeklyWorkouts());
        assertThat(savedGoal.getDescription()).isEqualTo(request.description());
        assertThat(savedGoal.getId()).isNotNull();
        assertThat(savedGoal.getCreatedAt()).isNotNull();

        assertThat(response.goalType()).isEqualTo(request.goalType());
        assertThat(response.goalNumber()).isEqualTo(request.goalNumber());
        assertThat(response.targetDate()).isEqualTo(request.targetDate());
        assertThat(response.weeklyWorkouts()).isEqualTo(request.weeklyWorkouts());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.id()).isEqualTo(savedGoal.getId());
        assertThat(response.createdAt()).isEqualTo(savedGoal.getCreatedAt());
    }
}
