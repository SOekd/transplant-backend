package com.transplantados.patient;

import com.transplantados.patient.dto.ChangePatientPasswordRequest;
import com.transplantados.patient.dto.CreatePatientRequest;
import com.transplantados.patient.dto.UpdatePatientRequest;
import com.transplantados.patient.exception.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient create(@NotNull @Validated CreatePatientRequest request) {
        Patient patient = Patient.builder()
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .password(passwordEncoder().encode(request.password()))
                .cellphone(request.cellphone())
                .alternativeCellphone(request.alternativeCellphone())
                .birthDate(request.birthDate())
                .bloodType(request.bloodType())
                .sex(request.sex())
                .build();

        return patientRepository.save(patient);
    }

    public Patient update(@NotNull @Validated UpdatePatientRequest patient) {

        val existingPatient = patientRepository.findById(patient.id())
                .orElseThrow(() -> new PatientNotFoundException(patient.id()));

        existingPatient.setName(patient.name());
        existingPatient.setCpf(patient.cpf());
        existingPatient.setEmail(patient.email());
        existingPatient.setCellphone(patient.cellphone());
        existingPatient.setAlternativeCellphone(patient.alternativeCellphone());
        existingPatient.setBirthDate(patient.birthDate());
        existingPatient.setBloodType(patient.bloodType());
        existingPatient.setSex(patient.sex());

        return patientRepository.save(existingPatient);
    }

    public void changePassword(@NotNull @Validated ChangePatientPasswordRequest request) {
        val existingPatient = patientRepository.findById(request.id()).orElseThrow(() -> new PatientNotFoundException(request.id()));
        existingPatient.setPassword(passwordEncoder().encode(request.password()));
        patientRepository.save(existingPatient);
    }

    public void remove(@NotNull @Validated UUID id) {
        val existingPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        patientRepository.delete(existingPatient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
