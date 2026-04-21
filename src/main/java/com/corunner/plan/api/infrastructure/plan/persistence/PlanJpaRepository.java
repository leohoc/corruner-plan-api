package com.corunner.plan.api.infrastructure.plan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanJpaRepository extends JpaRepository<PlanJpaEntity, UUID> {

    List<PlanJpaEntity> findByGoalId(UUID goalId);
}
