package com.iesfranciscodelosrios.repository;


import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolYearRepository extends CrudRepository<SchoolYear, UUID> {

    Optional<SchoolYear> findByNameAndAndInstitution(String name, Institution institution);
}
