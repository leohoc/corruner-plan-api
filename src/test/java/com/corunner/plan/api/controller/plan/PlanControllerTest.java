package com.corunner.plan.api.controller.plan;

import com.corunner.plan.api.application.plan.dto.CreatePlanRequest;
import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.usecase.CreatePlanUseCase;
import com.corunner.plan.api.application.plan.usecase.GetPlansByGoalUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanControllerTest {

    @Mock
    private CreatePlanUseCase createPlanUseCase;

    @Mock
    private GetPlansByGoalUseCase getPlansByGoalUseCase;

    @InjectMocks
    private PlanController planController;

    @Test
    void createPlan_returns201_andDelegatesToUseCase() {
        UUID goalId = UUID.randomUUID();
        CreatePlanRequest request = new CreatePlanRequest(goalId, "16-Week Plan", "Description");
        PlanResponse planResponse = new PlanResponse(UUID.randomUUID(), goalId, "16-Week Plan", "Description", Instant.now());

        when(createPlanUseCase.execute(any())).thenReturn(planResponse);

        ResponseEntity<PlanResponse> response = planController.createPlan(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(planResponse);
        verify(createPlanUseCase).execute(request);
    }

    @Test
    void getPlansByGoal_returns200_andDelegatesToUseCase() {
        UUID goalId = UUID.randomUUID();
        List<PlanResponse> plans = List.of(
                new PlanResponse(UUID.randomUUID(), goalId, "Plan A", null, Instant.now())
        );

        when(getPlansByGoalUseCase.execute(goalId)).thenReturn(plans);

        ResponseEntity<List<PlanResponse>> response = planController.getPlansByGoal(goalId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(plans);
        verify(getPlansByGoalUseCase).execute(goalId);
    }
}
