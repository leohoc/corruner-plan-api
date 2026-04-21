package com.corunner.plan.api.application.plan.port;

import com.corunner.plan.api.domain.plan.Plan;

import java.util.List;
import java.util.UUID;

public interface PlanRepository {

    Plan save(Plan plan);

    List<Plan> findByGoalId(UUID goalId);
}
