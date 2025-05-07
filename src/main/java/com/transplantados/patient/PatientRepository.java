package com.transplantados.patient;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends CrudRepository<Patient, UUID> {

    Optional<Patient> findByEmail(@NotNull String email);

    @NotNull List<Patient> findAll();

}
