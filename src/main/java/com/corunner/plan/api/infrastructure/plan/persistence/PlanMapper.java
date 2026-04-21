package com.corunner.plan.api.infrastructure.plan.persistence;

import com.corunner.plan.api.domain.plan.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public PlanJpaEntity toJpaEntity(Plan plan) {
        return new PlanJpaEntity(
                plan.getId(),
                plan.getGoalId(),
                plan.getName(),
                plan.getDescription(),
                plan.getCreatedAt()
        );
    }

    public Plan toDomain(PlanJpaEntity entity) {
        return new Plan(
                entity.getId(),
                entity.getGoalId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}
