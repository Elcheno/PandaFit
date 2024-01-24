package com.iesfranciscodelosrios.repository;


import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, UUID> {

    Optional<SchoolYear> findByNameAndAndInstitution(String name, Institution institution);

    Page<SchoolYear> findAllByInstitution(Institution institution, Pageable pageable) throws Exception;
}
