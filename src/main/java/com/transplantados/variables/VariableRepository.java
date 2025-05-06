package com.transplantados.variables;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VariableRepository extends CrudRepository<Variable, UUID> {

    void removeById(@NotNull UUID id);

    @NotNull List<Variable> findAll();

    @NotNull List<Variable> findAllByIdIn(@NotNull List<UUID> ids);

}
