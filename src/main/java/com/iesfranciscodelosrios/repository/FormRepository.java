package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormRepository extends CrudRepository<Form, UUID> {
    Optional<Form> findByName(String name);
}
