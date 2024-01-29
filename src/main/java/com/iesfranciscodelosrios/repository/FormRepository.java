package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormRepository extends CrudRepository<Form, UUID> {
    Optional<Form> findByName(String name);

    Page<Form> findAll(Pageable pageable) throws Exception;
}
