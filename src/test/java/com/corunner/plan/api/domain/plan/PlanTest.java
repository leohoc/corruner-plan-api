package com.corunner.plan.api.domain.plan;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PlanTest {

    @Test
    void create_generatesUniqueIdAndCreatedAt() {
        UUID goalId = UUID.randomUUID();

        Plan first = Plan.create(goalId, "Plan A", "Description A");
        Plan second = Plan.create(goalId, "Plan B", null);

        assertThat(first.getId()).isNotNull();
        assertThat(second.getId()).isNotNull();
        assertThat(first.getId()).isNotEqualTo(second.getId());
        assertThat(first.getCreatedAt()).isNotNull();
    }

    @Test
    void create_setsAllFields() {
        UUID goalId = UUID.randomUUID();

        Plan plan = Plan.create(goalId, "16-Week Marathon Plan", "Build up to 42km");

        assertThat(plan.getGoalId()).isEqualTo(goalId);
        assertThat(plan.getName()).isEqualTo("16-Week Marathon Plan");
        assertThat(plan.getDescription()).isEqualTo("Build up to 42km");
    }

    @Test
    void create_allowsNullDescription() {
        Plan plan = Plan.create(UUID.randomUUID(), "Plan without description", null);

        assertThat(plan.getDescription()).isNull();
    }
}
