package com.transplantados.alert.dto;

import com.transplantados.alert.ConditionOperator;
import com.transplantados.alert.LogicalOperator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateAlertRuleRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        LogicalOperator logicalOperator,

        boolean notifyPatient,

        boolean notifyMedicalTeam,

        @NotNull
        List<AlertConditionRequest> conditions
) {
    public record AlertConditionRequest(
            @NotNull
            UUID variableId,

            @NotNull
            ConditionOperator operator,

            @NotNull
            BigDecimal thresholdValue
    ) {
    }
}