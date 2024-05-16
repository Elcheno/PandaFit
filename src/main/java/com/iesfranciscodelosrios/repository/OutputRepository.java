package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OutputRepository extends CrudRepository<Output, UUID> {

    /**
     * Find an Output entity by its name.
     *
     * @param name The name of the Output entity.
     * @return An Optional containing the Output if found, or an empty Optional if not found.
     */
    Optional<Output> findByName(String name);

    /**
     * Retrieve all Output entities, paginated.
     *
     * @param pageable Pagination information.
     * @return A Page of Output entities.
     * @throws Exception If an error occurs during the operation.
     */
    Page<Output> findAll(Pageable pageable) throws Exception;

    /**
     * Retrieve all Output entities whose name contains the specified string, ignoring case.
     * @param pageable Pageable object for pagination.
     * @param name The string to search for in Output names.
     * @return A Page containing Output entities.
     * @throws Exception If an error occurs during retrieval.
     */
    Page<Output> findAllByNameContainingIgnoreCase(Pageable pageable, String name) throws Exception;
}
