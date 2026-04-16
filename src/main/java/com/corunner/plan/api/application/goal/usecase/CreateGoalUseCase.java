package com.corunner.plan.api.application.goal.usecase;

import com.corunner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corunner.plan.api.application.goal.dto.GoalResponse;

public interface CreateGoalUseCase {
    GoalResponse execute(CreateGoalRequest request);
}
