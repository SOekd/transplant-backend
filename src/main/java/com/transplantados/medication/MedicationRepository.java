package com.transplantados.medication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, UUID> {
    List<Medication> findByPatientId(UUID patientId);
}
