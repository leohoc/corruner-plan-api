package com.corunner.plan.api.application.plan.dto;

import com.corunner.plan.api.domain.plan.Plan;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PlanResponseTest {

    @Test
    void from_mapsAllFieldsFromPlan() {
        UUID id = UUID.randomUUID();
        UUID goalId = UUID.randomUUID();
        Instant createdAt = Instant.now();
        Plan plan = new Plan(id, goalId, "16-Week Marathon Plan", "Build up to 42km", createdAt);

        PlanResponse response = PlanResponse.from(plan);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.goalId()).isEqualTo(goalId);
        assertThat(response.name()).isEqualTo("16-Week Marathon Plan");
        assertThat(response.description()).isEqualTo("Build up to 42km");
        assertThat(response.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void from_preservesNullDescription() {
        Plan plan = new Plan(UUID.randomUUID(), UUID.randomUUID(), "Plan", null, Instant.now());

        PlanResponse response = PlanResponse.from(plan);

        assertThat(response.description()).isNull();
    }
}
