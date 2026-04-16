package com.corunner.plan.api.controller.goal;

import com.corunner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corunner.plan.api.application.goal.dto.GoalResponse;
import com.corunner.plan.api.application.goal.usecase.CreateGoalUseCase;
import com.corunner.plan.api.application.goal.usecase.GetGoalUseCase;
import com.corunner.plan.api.domain.goal.GoalType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalControllerTest {

    @Mock
    private CreateGoalUseCase createGoalUseCase;

    @Mock
    private GetGoalUseCase getGoalUseCase;

    @InjectMocks
    private GoalController controller;

    @Test
    void createGoal_returns201WithBody() {
        CreateGoalRequest request = new CreateGoalRequest(
                GoalType.DISTANCE, new BigDecimal("42.5"),
                LocalDate.of(2026, 12, 31), 3, "Marathon");
        GoalResponse expectedResponse = new GoalResponse(UUID.randomUUID(), GoalType.DISTANCE,
                new BigDecimal("42.5"), LocalDate.of(2026, 12, 31), 3, "Marathon", Instant.now());
        when(createGoalUseCase.execute(request)).thenReturn(expectedResponse);

        ResponseEntity<GoalResponse> result = controller.createGoal(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void createGoal_delegatesToCreateGoalUseCase() {
        CreateGoalRequest request = new CreateGoalRequest(
                GoalType.TIME, new BigDecimal("60.0"),
                LocalDate.of(2026, 6, 30), 2, null);
        when(createGoalUseCase.execute(request)).thenReturn(
                new GoalResponse(UUID.randomUUID(), GoalType.TIME, new BigDecimal("60.0"),
                        LocalDate.of(2026, 6, 30), 2, null, Instant.now()));

        controller.createGoal(request);

        verify(createGoalUseCase).execute(request);
    }

    @Test
    void getGoalById_returns200WithBody() {
        UUID id = UUID.randomUUID();
        GoalResponse expectedResponse = new GoalResponse(id, GoalType.PACE, new BigDecimal("5.5"),
                LocalDate.of(2026, 12, 31), 4, "Pace goal", Instant.now());
        when(getGoalUseCase.execute(id)).thenReturn(expectedResponse);

        ResponseEntity<GoalResponse> result = controller.getGoalById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void getGoalById_delegatesToGetGoalUseCase() {
        UUID id = UUID.randomUUID();
        when(getGoalUseCase.execute(id)).thenReturn(
                new GoalResponse(id, GoalType.FREQUENCY, BigDecimal.TEN,
                        LocalDate.now(), 5, null, Instant.now()));

        controller.getGoalById(id);

        verify(getGoalUseCase).execute(id);
    }
}
