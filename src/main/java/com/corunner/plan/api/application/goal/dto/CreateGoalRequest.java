package com.corunner.plan.api.application.goal.dto;

import com.corunner.plan.api.domain.goal.GoalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateGoalRequest(

        @NotNull
        GoalType goalType,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal goalNumber,

        @NotNull
        LocalDate targetDate,

        @NotNull
        @Min(1)
        Integer weeklyWorkouts,

        String description
) {}
