package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Input;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InputRepository extends CrudRepository<Input, UUID> {

    Optional<Input> findByName(String name);
}