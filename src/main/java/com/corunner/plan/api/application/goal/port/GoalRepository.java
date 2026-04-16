package com.corunner.plan.api.application.goal.port;

import com.corunner.plan.api.domain.goal.Goal;

import java.util.Optional;
import java.util.UUID;

public interface GoalRepository {
    Goal save(Goal goal);
    Optional<Goal> findById(UUID id);
}
