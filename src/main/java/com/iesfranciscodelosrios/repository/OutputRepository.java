package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Output;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutputRepository extends CrudRepository<Output, UUID> {

    Optional<Output> findByName(String name);

    Optional<Output> findByFormula(String formula);
}
