package com.corruner.plan.api.application.goal.port;

import com.corruner.plan.api.domain.goal.Goal;

import java.util.Optional;
import java.util.UUID;

public interface GoalRepository {
    Goal save(Goal goal);
    Optional<Goal> findById(UUID id);
}
