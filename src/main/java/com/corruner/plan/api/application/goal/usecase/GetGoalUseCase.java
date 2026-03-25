package com.corruner.plan.api.application.goal.usecase;

import com.corruner.plan.api.application.goal.dto.GoalResponse;

import java.util.UUID;

public interface GetGoalUseCase {
    GoalResponse execute(UUID id);
}
