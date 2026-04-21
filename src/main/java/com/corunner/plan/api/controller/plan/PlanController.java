package com.corunner.plan.api.controller.plan;

import com.corunner.plan.api.application.plan.dto.CreatePlanRequest;
import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.usecase.CreatePlanUseCase;
import com.corunner.plan.api.application.plan.usecase.GetPlansByGoalUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Plans", description = "Plan management endpoints")
public class PlanController {

    private static final Logger log = LoggerFactory.getLogger(PlanController.class);

    private final CreatePlanUseCase createPlanUseCase;
    private final GetPlansByGoalUseCase getPlansByGoalUseCase;

    public PlanController(CreatePlanUseCase createPlanUseCase, GetPlansByGoalUseCase getPlansByGoalUseCase) {
        this.createPlanUseCase = createPlanUseCase;
        this.getPlansByGoalUseCase = getPlansByGoalUseCase;
    }

    @PostMapping("/api/v1/plans")
    @Operation(summary = "Create a plan", description = "Creates a new training plan linked to a goal")
    public ResponseEntity<PlanResponse> createPlan(@Valid @RequestBody CreatePlanRequest request) {
        log.info("POST /api/v1/plans - goalId={}", request.goalId());
        PlanResponse response = createPlanUseCase.execute(request);
        log.info("Plan created with id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/v1/goals/{goalId}/plans")
    @Operation(summary = "Get plans by goal", description = "Returns all training plans linked to a given goal")
    public ResponseEntity<List<PlanResponse>> getPlansByGoal(@PathVariable UUID goalId) {
        log.info("GET /api/v1/goals/{}/plans", goalId);
        return ResponseEntity.ok(getPlansByGoalUseCase.execute(goalId));
    }
}
