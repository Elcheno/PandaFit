package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionRepository extends CrudRepository<Institution, UUID> {
    /**
     * Finds an institution by its name.
     *
     * @param name The name of the institution.
     * @return An optional containing the institution if found, otherwise empty.
     */
    Optional<Institution> findByName(String name);

    /**
     * Retrieves all institutions paginated.
     *
     * @param pageable Pagination information.
     * @return A page of institutions.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Institution> findAll(Pageable pageable) throws Exception;

    /**
     * Retrieves all institutions containing the specified name (case-insensitive) paginated.
     *
     * @param pageable Pagination information.
     * @param name     The name to search for.
     * @return A page of institutions containing the specified name.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Institution> findAllByNameContainingIgnoreCase(Pageable pageable, String name) throws Exception;
}
