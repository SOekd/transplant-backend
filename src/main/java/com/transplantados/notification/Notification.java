package com.transplantados.notification;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean patientMark = false;

    private boolean toAllProfessionals = false;

    private String title;

    private String message;

    private UUID patientId;

    @ElementCollection
    @CollectionTable(name = "notification_professionals", joinColumns = @JoinColumn(name = "notification_id"))
    @Column(name = "professional_id")
    private List<UUID> professionals;

}
