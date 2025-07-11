package com.transplantados.professional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessionalRepository extends CrudRepository<Professional, UUID> {

    Optional<Professional> findByEmail(@NotNull String email);

    @NotNull List<Professional> findAll();

}
