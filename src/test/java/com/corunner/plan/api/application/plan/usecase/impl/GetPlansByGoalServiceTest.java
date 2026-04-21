package com.corunner.plan.api.application.plan.usecase.impl;

import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.port.PlanRepository;
import com.corunner.plan.api.domain.plan.Plan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPlansByGoalServiceTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private GetPlansByGoalService getPlansByGoalService;

    @Test
    void execute_returnsMappedPlans_whenPlansExist() {
        UUID goalId = UUID.randomUUID();
        Plan plan1 = new Plan(UUID.randomUUID(), goalId, "Plan A", "Desc A", Instant.now());
        Plan plan2 = new Plan(UUID.randomUUID(), goalId, "Plan B", null, Instant.now());

        when(planRepository.findByGoalId(goalId)).thenReturn(List.of(plan1, plan2));

        List<PlanResponse> result = getPlansByGoalService.execute(goalId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Plan A");
        assertThat(result.get(0).description()).isEqualTo("Desc A");
        assertThat(result.get(1).name()).isEqualTo("Plan B");
        assertThat(result.get(1).description()).isNull();
    }

    @Test
    void execute_returnsEmptyList_whenNoPlansExist() {
        UUID goalId = UUID.randomUUID();
        when(planRepository.findByGoalId(goalId)).thenReturn(List.of());

        List<PlanResponse> result = getPlansByGoalService.execute(goalId);

        assertThat(result).isEmpty();
    }
}
