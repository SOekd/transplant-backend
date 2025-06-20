package com.transplantados.variables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.transplantados.transplant.TransplantLogBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "variable_input")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VariableInput {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "logbook_id")
    @JsonBackReference
    private TransplantLogBook logBook;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_id")
    private Variable variable;

    @NotNull
    private BigDecimal value;

}
