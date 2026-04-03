package com.corruner.plan.api.controller.goal;

import com.corruner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corruner.plan.api.application.goal.dto.GoalResponse;
import com.corruner.plan.api.application.goal.usecase.CreateGoalUseCase;
import com.corruner.plan.api.application.goal.usecase.GetGoalUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/goals")
@Tag(name = "Goals", description = "Goal management endpoints")
public class GoalController {

    private static final Logger log = LoggerFactory.getLogger(GoalController.class);

    private final CreateGoalUseCase createGoalUseCase;
    private final GetGoalUseCase getGoalUseCase;

    public GoalController(CreateGoalUseCase createGoalUseCase, GetGoalUseCase getGoalUseCase) {
        this.createGoalUseCase = createGoalUseCase;
        this.getGoalUseCase = getGoalUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a goal", description = "Creates a new running goal")
    public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody CreateGoalRequest request) {
        log.info("POST /api/v1/goals - goalType={}", request.goalType());
        GoalResponse response = createGoalUseCase.execute(request);
        log.info("Goal created with id={}", response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a goal by ID", description = "Retrieves a running goal by its UUID")
    public ResponseEntity<GoalResponse> getGoalById(@PathVariable UUID id) {
        log.info("GET /api/v1/goals/{}", id);
        return ResponseEntity.ok(getGoalUseCase.execute(id));
    }
}
