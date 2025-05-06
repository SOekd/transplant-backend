package com.transplantados.variables;

import com.transplantados.transplant.TransplantLogBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private TransplantLogBook logBook;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_id")
    private Variable variable;

    @NotBlank
    private BigDecimal value;

}
