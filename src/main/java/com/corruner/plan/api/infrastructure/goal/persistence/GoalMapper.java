package com.corruner.plan.api.infrastructure.goal.persistence;

import com.corruner.plan.api.domain.goal.Goal;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    public GoalJpaEntity toJpaEntity(Goal goal) {
        return new GoalJpaEntity(
                goal.getId(),
                goal.getGoalType(),
                goal.getGoalNumber(),
                goal.getTargetDate(),
                goal.getWeeklyWorkouts(),
                goal.getDescription(),
                goal.getCreatedAt()
        );
    }

    public Goal toDomain(GoalJpaEntity entity) {
        return new Goal(
                entity.getId(),
                entity.getGoalType(),
                entity.getGoalNumber(),
                entity.getTargetDate(),
                entity.getWeeklyWorkouts(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}
