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
}
