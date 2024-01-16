package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.FormAct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormActRepository extends CrudRepository <FormAct, UUID> {

    Optional<FormAct> findFormActByStartDate(UUID id);
}
