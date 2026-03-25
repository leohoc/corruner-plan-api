package com.corruner.plan.api.infrastructure.goal.persistence;

import com.corruner.plan.api.application.goal.port.GoalRepository;
import com.corruner.plan.api.domain.goal.Goal;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GoalRepositoryAdapter implements GoalRepository {

    private final GoalJpaRepository jpaRepository;
    private final GoalMapper mapper;

    public GoalRepositoryAdapter(GoalJpaRepository jpaRepository, GoalMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Goal save(Goal goal) {
        GoalJpaEntity entity = mapper.toJpaEntity(goal);
        GoalJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Goal> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
