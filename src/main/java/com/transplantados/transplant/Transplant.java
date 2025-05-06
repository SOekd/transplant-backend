package com.transplantados.transplant;

import com.transplantados.variables.Variable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "alert_rule")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transplant {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @ManyToMany
    @JoinTable(
        name = "alert_rule_variable",
        joinColumns = @JoinColumn(name = "alert_rule_id"),
        inverseJoinColumns = @JoinColumn(name = "variable_id")
    )
    private List<Variable> variables;



}
