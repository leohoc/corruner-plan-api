package com.corunner.plan.api.application.goal.usecase.impl;

import com.corunner.plan.api.application.goal.dto.CreateGoalRequest;
import com.corunner.plan.api.application.goal.dto.GoalResponse;
import com.corunner.plan.api.application.goal.port.GoalRepository;
import com.corunner.plan.api.application.goal.usecase.CreateGoalUseCase;
import com.corunner.plan.api.domain.goal.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateGoalService implements CreateGoalUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateGoalService.class);

    private final GoalRepository goalRepository;

    public CreateGoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public GoalResponse execute(CreateGoalRequest request) {
        log.debug("Creating goal: goalType={}, goalNumber={}, targetDate={}, weeklyWorkouts={}",
                request.goalType(), request.goalNumber(), request.targetDate(), request.weeklyWorkouts());
        Goal goal = Goal.create(
                request.goalType(),
                request.goalNumber(),
                request.targetDate(),
                request.weeklyWorkouts(),
                request.description()
        );
        Goal saved = goalRepository.save(goal);
        log.debug("Goal saved: id={}", saved.getId());
        return GoalResponse.from(saved);
    }
}
