package com.corunner.plan.api.infrastructure.goal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoalJpaRepository extends JpaRepository<GoalJpaEntity, UUID> {
}
