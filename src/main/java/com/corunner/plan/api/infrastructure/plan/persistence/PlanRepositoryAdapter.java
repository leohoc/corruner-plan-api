package com.corunner.plan.api.infrastructure.plan.persistence;

import com.corunner.plan.api.application.plan.port.PlanRepository;
import com.corunner.plan.api.domain.plan.Plan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlanRepositoryAdapter implements PlanRepository {

    private final PlanJpaRepository jpaRepository;
    private final PlanMapper mapper;

    public PlanRepositoryAdapter(PlanJpaRepository jpaRepository, PlanMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Plan save(Plan plan) {
        PlanJpaEntity entity = mapper.toJpaEntity(plan);
        PlanJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Plan> findByGoalId(UUID goalId) {
        return jpaRepository.findByGoalId(goalId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
