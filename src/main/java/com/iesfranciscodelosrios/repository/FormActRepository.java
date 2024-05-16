package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormActRepository extends CrudRepository <FormAct, UUID> {

    /**
     * Finds a form activity by its start date.
     *
     * @param date The start date of the form activity.
     * @return An optional containing the form activity if found, otherwise empty.
     */
    Optional<FormAct> findFormActByStartDate(LocalDateTime date);

    /**
     * Finds a form activity by its associated form.
     *
     * @param form The form associated with the form activity.
     * @return An optional containing the form activity if found, otherwise empty.
     */
    Optional<FormAct> findByForm(Form form);

    /**
     * Retrieves all form activities associated with a specific form, paginated.
     *
     * @param form     The form associated with the form activities.
     * @param pageable Pagination information.
     * @return A page of form activities.
     */
    Page<FormAct> findAllByForm(Form form, Pageable pageable);

    /**
     * Retrieves all form activities.
     *
     * @return A list of all form activities.
     */
    List<FormAct> findAll();

    /**
     * Retrieves all form activities paginated.
     *
     * @param pageable Pagination information.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAll(Pageable pageable) throws Exception;

    /**
     * Retrieves all form activities associated with a specific school year, paginated.
     *
     * @param schoolYear The school year associated with the form activities.
     * @param pageable   Pagination information.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllBySchoolYear(SchoolYear schoolYear, Pageable pageable) throws Exception;

    /**
     * Retrieves all form activities associated with a specific school year and expiration date after a given date, paginated.
     *
     * @param schoolYear The school year associated with the form activities.
     * @param date       The expiration date threshold.
     * @param pageable   Pagination information.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllBySchoolYearAndExpirationDateAfter(SchoolYear schoolYear, LocalDateTime date, Pageable pageable) throws Exception;

    /**
     * Retrieves all form activities associated with a specific school year and expiration date before a given date, paginated.
     *
     * @param schoolYear The school year associated with the form activities.
     * @param date       The expiration date threshold.
     * @param pageable   Pagination information.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllBySchoolYearAndExpirationDateBefore(SchoolYear schoolYear, LocalDateTime date, Pageable pageable) throws Exception;

    /**
     * Retrieves all form activities associated with a specific name, paginated.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllByFormNameContainingIgnoreCase(Pageable pageable, String name) throws Exception;

    /**
     * Retrieves all form activities associated with a specific name and expiration date after a given date, paginated.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @param date The expiration date threshold.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllByFormNameContainingIgnoreCaseAndExpirationDateAfter(Pageable pageable, String name, LocalDateTime date) throws Exception;

    /**
     * Retrieves all form activities associated with a specific name and expiration date before a given date, paginated.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @param date The expiration date threshold.
     * @return A page of form activities.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<FormAct> findAllByFormNameContainingIgnoreCaseAndExpirationDateBefore(Pageable pageable, String name, LocalDateTime date) throws Exception;
}
