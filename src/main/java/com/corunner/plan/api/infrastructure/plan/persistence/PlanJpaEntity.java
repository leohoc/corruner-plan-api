package com.corunner.plan.api.infrastructure.plan.persistence;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "plans")
public class PlanJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "goal_id", nullable = false, updatable = false)
    private UUID goalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected PlanJpaEntity() {}

    public PlanJpaEntity(UUID id, UUID goalId, String name, String description, Instant createdAt) {
        this.id = id;
        this.goalId = goalId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getGoalId() { return goalId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
