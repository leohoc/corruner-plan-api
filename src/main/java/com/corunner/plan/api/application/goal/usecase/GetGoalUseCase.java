package com.corunner.plan.api.application.goal.usecase;

import com.corunner.plan.api.application.goal.dto.GoalResponse;

import java.util.UUID;

public interface GetGoalUseCase {
    GoalResponse execute(UUID id);
}
