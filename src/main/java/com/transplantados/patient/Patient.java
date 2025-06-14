package com.transplantados.patient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.transplantados.medication.Medication;
import com.transplantados.transplant.Transplant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String cellphone;

    @NotNull
    @NotBlank
    private String alternativeCellphone;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private BloodType bloodType;

    @NotNull
    private Sex sex;

    @ManyToMany
    @JoinTable(
            name = "patient_transplant",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "transplant_id")
    )
    private List<Transplant> transplants;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Medication> medications = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

}
