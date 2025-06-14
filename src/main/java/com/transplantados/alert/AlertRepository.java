package com.transplantados.alert;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlertRepository extends CrudRepository<Alert, UUID> {

    List<Alert> findByConfirmed(boolean confirmed);

    @NotNull List<Alert> findAll();

}
