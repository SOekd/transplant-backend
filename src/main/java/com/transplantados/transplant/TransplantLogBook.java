package com.transplantados.transplant;

import com.transplantados.patient.Patient;
import com.transplantados.variables.VariableInput;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transplant_logbook")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransplantLogBook {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "logBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariableInput> inputs = new ArrayList<>();

}