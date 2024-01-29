package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InputRepository extends CrudRepository<Input, UUID> {

    Optional<Input> findByName(String name);

    Page<Input> findAll(Pageable pageable) throws Exception;
}
