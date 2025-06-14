package com.transplantados.transplant;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransplantRepository extends CrudRepository<Transplant, UUID> {

    void removeById(@NotNull UUID id);

    @NotNull List<Transplant> findAll();

    @NotNull List<Transplant> findAllById(UUID id);

}
