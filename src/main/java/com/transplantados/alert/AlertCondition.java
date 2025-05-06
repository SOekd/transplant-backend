package com.transplantados.alert;

import com.transplantados.variables.Variable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "alert_condition")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlertCondition {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    private AlertRule rule;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_id")
    private Variable variable;

    @Enumerated(EnumType.STRING)
    private ConditionOperator operator;

    private BigDecimal thresholdValue;
}