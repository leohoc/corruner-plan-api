package com.corruner.plan.api.application.goal.usecase.impl;

import com.corruner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corruner.plan.api.application.goal.dto.GoalResponse;
import com.corruner.plan.api.application.goal.port.GoalRepository;
import com.corruner.plan.api.application.goal.usecase.CreateGoalUseCase;
import com.corruner.plan.api.domain.goal.Goal;
import org.springframework.stereotype.Service;

@Service
public class CreateGoalService implements CreateGoalUseCase {

    private final GoalRepository goalRepository;

    public CreateGoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public GoalResponse execute(CreateGoalRequest request) {
        Goal goal = Goal.create(
                request.goalType(),
                request.goalNumber(),
                request.targetDate(),
                request.weeklyWorkouts(),
                request.description()
        );
        Goal saved = goalRepository.save(goal);
        return GoalResponse.from(saved);
    }
}
