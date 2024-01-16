package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.FormAct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormActRepository extends CrudRepository <FormAct, UUID> {

    Optional<FormAct> findFormActByStartDate(LocalDateTime date);
}
