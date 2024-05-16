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
    /**
     * Finds a form by its name.
     *
     * @param name The name of the form.
     * @return An optional containing the form if found, otherwise empty.
     */
    Optional<Form> findByName(String name);

    /**
     * Retrieves all forms paginated.
     *
     * @param pageable Pagination information.
     * @return A page of forms.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Form> findAll(Pageable pageable) throws Exception;

    /**
     * Retrieves all forms containing the specified name (case-insensitive) paginated.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @return A page of forms containing the specified name.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Form> findAllByNameContainingIgnoreCase(Pageable pageable, String name) throws Exception;
}
