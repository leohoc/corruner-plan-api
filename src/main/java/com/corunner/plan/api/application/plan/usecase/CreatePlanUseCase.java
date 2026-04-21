package com.corunner.plan.api.application.plan.usecase;

import com.corunner.plan.api.application.plan.dto.CreatePlanRequest;
import com.corunner.plan.api.application.plan.dto.PlanResponse;

public interface CreatePlanUseCase {

    PlanResponse execute(CreatePlanRequest request);
}
