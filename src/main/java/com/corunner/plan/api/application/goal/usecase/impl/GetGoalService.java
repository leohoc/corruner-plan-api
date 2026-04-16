package com.corunner.plan.api.application.goal.usecase.impl;

import com.corunner.plan.api.application.goal.dto.GoalResponse;
import com.corunner.plan.api.application.goal.port.GoalRepository;
import com.corunner.plan.api.application.goal.usecase.GetGoalUseCase;
import com.corunner.plan.api.controller.goal.exception.GoalNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetGoalService implements GetGoalUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetGoalService.class);

    private final GoalRepository goalRepository;

    public GetGoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public GoalResponse execute(UUID id) {
        log.debug("Fetching goal: id={}", id);
        return goalRepository.findById(id)
                .map(GoalResponse::from)
                .orElseThrow(() -> {
                    log.warn("Goal not found: id={}", id);
                    return new GoalNotFoundException(id);
                });
    }
}
