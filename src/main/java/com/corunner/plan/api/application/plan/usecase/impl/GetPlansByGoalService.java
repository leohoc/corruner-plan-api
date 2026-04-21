package com.corunner.plan.api.application.plan.usecase.impl;

import com.corunner.plan.api.application.plan.dto.PlanResponse;
import com.corunner.plan.api.application.plan.port.PlanRepository;
import com.corunner.plan.api.application.plan.usecase.GetPlansByGoalUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetPlansByGoalService implements GetPlansByGoalUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetPlansByGoalService.class);

    private final PlanRepository planRepository;

    public GetPlansByGoalService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<PlanResponse> execute(UUID goalId) {
        log.debug("Getting plans for goalId={}", goalId);
        List<PlanResponse> plans = planRepository.findByGoalId(goalId)
                .stream()
                .map(PlanResponse::from)
                .toList();
        log.debug("Found {} plan(s) for goalId={}", plans.size(), goalId);
        return plans;
    }
}
