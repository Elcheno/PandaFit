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

    /**
     * Finds an input by its name.
     *
     * @param name The name of the input.
     * @return An optional containing the input if found, otherwise empty.
     */
    Optional<Input> findByName(String name);

    /**
     * Retrieves all inputs paginated.
     *
     * @param pageable Pagination information.
     * @return A page of inputs.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Input> findAll(Pageable pageable) throws Exception;
}
