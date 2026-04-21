package com.corunner.plan.api.application.plan.usecase.impl;

import com.corunner.plan.api.application.goal.port.GoalRepository;
import com.corunner.plan.api.application.plan.dto.CreatePlanRequest;
import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.port.PlanRepository;
import com.corunner.plan.api.application.plan.usecase.CreatePlanUseCase;
import com.corunner.plan.api.controller.goal.exception.GoalNotFoundException;
import com.corunner.plan.api.domain.plan.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreatePlanService implements CreatePlanUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreatePlanService.class);

    private final PlanRepository planRepository;
    private final GoalRepository goalRepository;

    public CreatePlanService(PlanRepository planRepository, GoalRepository goalRepository) {
        this.planRepository = planRepository;
        this.goalRepository = goalRepository;
    }

    @Override
    public PlanResponse execute(CreatePlanRequest request) {
        log.debug("Creating plan: goalId={}, name={}", request.goalId(), request.name());

        goalRepository.findById(request.goalId())
                .orElseThrow(() -> new GoalNotFoundException(request.goalId()));

        Plan plan = Plan.create(request.goalId(), request.name(), request.description());
        Plan saved = planRepository.save(plan);

        log.debug("Plan saved: id={}", saved.getId());
        return PlanResponse.from(saved);
    }
}
