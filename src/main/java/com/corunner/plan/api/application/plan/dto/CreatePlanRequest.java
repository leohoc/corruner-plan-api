package com.corunner.plan.api.application.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePlanRequest(

        @NotNull
        UUID goalId,

        @NotBlank
        String name,

        String description
) {}