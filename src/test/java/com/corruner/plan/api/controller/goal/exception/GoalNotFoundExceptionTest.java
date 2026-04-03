package com.corruner.plan.api.controller.goal.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GoalNotFoundExceptionTest {

    @Test
    void message_containsUUID() {
        UUID id = UUID.randomUUID();

        GoalNotFoundException ex = new GoalNotFoundException(id);

        assertThat(ex.getMessage()).contains(id.toString());
    }

    @Test
    void message_matchesExpectedFormat() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");

        GoalNotFoundException ex = new GoalNotFoundException(id);

        assertThat(ex.getMessage()).isEqualTo("Goal not found with id: 00000000-0000-0000-0000-000000000001");
    }
}
