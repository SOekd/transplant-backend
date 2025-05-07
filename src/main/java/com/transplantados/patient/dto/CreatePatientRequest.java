package com.transplantados.patient.dto;

import com.transplantados.patient.BloodType;
import com.transplantados.patient.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record CreatePatientRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        String cpf,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String cellphone,

        @NotNull
        @NotBlank
        String alternativeCellphone,

        @NotNull
        LocalDate birthDate,

        @NotNull
        BloodType bloodType,

        @NotNull
        Sex sex
) {
}
