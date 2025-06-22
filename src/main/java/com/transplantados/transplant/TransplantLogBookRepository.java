package com.transplantados.transplant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransplantLogBookRepository extends CrudRepository<TransplantLogBook, UUID> {
    List<TransplantLogBook> findByPatientId(UUID patientId);
}
