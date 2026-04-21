package com.corunner.plan.api.infrastructure.plan.persistence;

import com.corunner.plan.api.domain.plan.Plan;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PlanMapperTest {

    private final PlanMapper mapper = new PlanMapper();

    @Test
    void toJpaEntity_mapsAllFields() {
        UUID id = UUID.randomUUID();
        UUID goalId = UUID.randomUUID();
        Instant createdAt = Instant.now();
        Plan plan = new Plan(id, goalId, "16-Week Marathon Plan", "Build up to 42km", createdAt);

        PlanJpaEntity entity = mapper.toJpaEntity(plan);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getGoalId()).isEqualTo(goalId);
        assertThat(entity.getName()).isEqualTo("16-Week Marathon Plan");
        assertThat(entity.getDescription()).isEqualTo("Build up to 42km");
        assertThat(entity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toDomain_mapsAllFields() {
        UUID id = UUID.randomUUID();
        UUID goalId = UUID.randomUUID();
        Instant createdAt = Instant.now();
        PlanJpaEntity entity = new PlanJpaEntity(id, goalId, "16-Week Marathon Plan", "Build up to 42km", createdAt);

        Plan plan = mapper.toDomain(entity);

        assertThat(plan.getId()).isEqualTo(id);
        assertThat(plan.getGoalId()).isEqualTo(goalId);
        assertThat(plan.getName()).isEqualTo("16-Week Marathon Plan");
        assertThat(plan.getDescription()).isEqualTo("Build up to 42km");
        assertThat(plan.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void toJpaEntity_preservesNullDescription() {
        Plan plan = new Plan(UUID.randomUUID(), UUID.randomUUID(), "Plan", null, Instant.now());

        assertThat(mapper.toJpaEntity(plan).getDescription()).isNull();
    }

    @Test
    void toDomain_preservesNullDescription() {
        PlanJpaEntity entity = new PlanJpaEntity(UUID.randomUUID(), UUID.randomUUID(), "Plan", null, Instant.now());

        assertThat(mapper.toDomain(entity).getDescription()).isNull();
    }
}
