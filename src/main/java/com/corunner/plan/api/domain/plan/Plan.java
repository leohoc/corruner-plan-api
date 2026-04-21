package com.corunner.plan.api.domain.plan;

import java.time.Instant;
import java.util.UUID;

public class Plan {

    private final UUID id;
    private final UUID goalId;
    private final String name;
    private final String description;
    private final Instant createdAt;

    public Plan(UUID id, UUID goalId, String name, String description, Instant createdAt) {
        this.id = id;
        this.goalId = goalId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Plan create(UUID goalId, String name, String description) {
        return new Plan(
                UUID.randomUUID(),
                goalId,
                name,
                description,
                Instant.now()
        );
    }

    public UUID getId() { return id; }
    public UUID getGoalId() { return goalId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
