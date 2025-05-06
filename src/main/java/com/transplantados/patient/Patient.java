package com.transplantados.patient;

import com.transplantados.transplant.Transplant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 200)
    @Column(nullable = false, length = 200)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 11, max = 11)
    @Column(nullable = false, length = 11)
    private String cpf;

    @Nullable
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String cellphone;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(length = 100)
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

}
