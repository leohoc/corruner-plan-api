package com.corruner.plan.api.application.goal.usecase;

import com.corruner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corruner.plan.api.application.goal.dto.GoalResponse;

public interface CreateGoalUseCase {
    GoalResponse execute(CreateGoalRequest request);
}
