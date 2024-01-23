package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormActRepository extends CrudRepository <FormAct, UUID> {

    Optional<FormAct> findFormActByStartDate(LocalDateTime date);

    Optional<FormAct> findByForm(Form form);

    Page<FormAct> findAllByForm(Form form, Pageable pageable);

    List<FormAct> findAll();
}
