package com.transplantados.alert;

import com.transplantados.patient.Patient;
import com.transplantados.transplant.TransplantLogBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alert")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Alert {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    private AlertRule rule;

    @ManyToOne(optional = false)
    @JoinColumn(name = "logbook_id")
    private TransplantLogBook logBook;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private boolean confirmed = false;

    private LocalDateTime confirmedAt;

    private LocalDateTime triggeredAt = LocalDateTime.now();

}