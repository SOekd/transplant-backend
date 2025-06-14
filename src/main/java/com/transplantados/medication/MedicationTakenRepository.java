package com.transplantados.medication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicationTakenRepository extends CrudRepository<MedicationTaken, UUID> {
}
