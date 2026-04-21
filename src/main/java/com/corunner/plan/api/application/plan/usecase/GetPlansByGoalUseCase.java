package com.corunner.plan.api.application.plan.usecase;

import com.corunner.plan.api.application.plan.dto.PlanResponse;

import java.util.List;
import java.util.UUID;

public interface GetPlansByGoalUseCase {

    List<PlanResponse> execute(UUID goalId);
}
