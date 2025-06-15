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
@Table(name = "transplant")
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
            name = "transplant_variable",
            joinColumns = @JoinColumn(name = "transplant_id"),
            inverseJoinColumns = @JoinColumn(name = "variable_id")
    )
    private List<Variable> variables;

}
