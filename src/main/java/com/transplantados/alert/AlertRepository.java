package com.transplantados.alert;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlertRepository extends CrudRepository<Alert, UUID> {
}
