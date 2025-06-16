package com.transplantados.notification;

import com.transplantados.professional.Professional;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "idx_notification_professional", columnList = "professionalId"),
        @Index(name = "idx_notification_patient", columnList = "patientId"),
        @Index(name = "idx_notification_read", columnList = "read"),
        @Index(name = "idx_notification_to_all_professionals", columnList = "toAllProfessionals")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID patientId;

    private boolean read = false;

    private boolean toAllProfessionals = false;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "notification_professional",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id")
    )
    private List<Professional> professionals;

    private String title;

    private String message;

}
