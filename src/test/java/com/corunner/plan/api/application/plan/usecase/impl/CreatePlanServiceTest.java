package com.corunner.plan.api.application.plan.usecase.impl;

import com.corunner.plan.api.application.goal.port.GoalRepository;
import com.corunner.plan.api.application.plan.dto.CreatePlanRequest;
import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.port.PlanRepository;
import com.corunner.plan.api.controller.goal.exception.GoalNotFoundException;
import com.corunner.plan.api.domain.goal.Goal;
import com.corunner.plan.api.domain.goal.GoalType;
import com.corunner.plan.api.domain.plan.Plan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private CreatePlanService createPlanService;

    @Test
    void execute_savesAndReturnsPlan_whenGoalExists() {
        UUID goalId = UUID.randomUUID();
        Goal goal = new Goal(goalId, GoalType.DISTANCE, new BigDecimal("42.5"),
                LocalDate.of(2026, 12, 31), 3, "Marathon", Instant.now());
        CreatePlanRequest request = new CreatePlanRequest(goalId, "16-Week Marathon Plan", "Build up to 42km");

        when(goalRepository.findById(goalId)).thenReturn(Optional.of(goal));
        when(planRepository.save(any(Plan.class))).thenAnswer(inv -> inv.getArgument(0));

        PlanResponse response = createPlanService.execute(request);

        ArgumentCaptor<Plan> planCaptor = ArgumentCaptor.forClass(Plan.class);
        verify(planRepository).save(planCaptor.capture());
        Plan saved = planCaptor.getValue();

        assertThat(saved.getGoalId()).isEqualTo(goalId);
        assertThat(saved.getName()).isEqualTo("16-Week Marathon Plan");
        assertThat(saved.getDescription()).isEqualTo("Build up to 42km");
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();

        assertThat(response.id()).isEqualTo(saved.getId());
        assertThat(response.goalId()).isEqualTo(goalId);
        assertThat(response.name()).isEqualTo("16-Week Marathon Plan");
        assertThat(response.description()).isEqualTo("Build up to 42km");
    }

    @Test
    void execute_throwsGoalNotFoundException_whenGoalDoesNotExist() {
        UUID goalId = UUID.randomUUID();
        CreatePlanRequest request = new CreatePlanRequest(goalId, "Some Plan", null);

        when(goalRepository.findById(goalId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createPlanService.execute(request))
                .isInstanceOf(GoalNotFoundException.class)
                .hasMessageContaining(goalId.toString());
    }
}
