package com.corruner.plan.api.application.goal.usecase.impl;

import com.corruner.plan.api.application.goal.dto.GoalResponse;
import com.corruner.plan.api.application.goal.port.GoalRepository;
import com.corruner.plan.api.application.goal.usecase.GetGoalUseCase;
import com.corruner.plan.api.controller.goal.exception.GoalNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetGoalService implements GetGoalUseCase {

    private final GoalRepository goalRepository;

    public GetGoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public GoalResponse execute(UUID id) {
        return goalRepository.findById(id)
                .map(GoalResponse::from)
                .orElseThrow(() -> new GoalNotFoundException(id));
    }
}
