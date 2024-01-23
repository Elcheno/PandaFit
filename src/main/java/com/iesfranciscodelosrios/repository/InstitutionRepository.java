package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, UUID> {
    Optional<Institution> findByName(String name);

    Page<Institution> findAll(Pageable pageable) throws Exception;
}
