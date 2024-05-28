package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AnswerRepository extends CrudRepository<Answer, UUID> {
    /**
     * Finds an answer by its date.
     *
     * @param date The date of the answer.
     * @return An optional containing the answer if found, otherwise empty.
     */
    @Query(
            value = "SELECT * FROM answer a WHERE a.date = ?1",
            nativeQuery = true)
    Optional<Answer> findAnswerByDate(LocalDateTime date);

    /**
     * Find an Answer entity by its name.
     *
     * @param pageable The name of the Answer entity.
     * @return An Optional containing the Output if found, or an empty Optional if not found.
     */
    Page<Answer> findAllByFormAct_SchoolYearAndUuidContainingIgnoreCase(Pageable pageable, SchoolYear schoolYear, String uuid) throws Exception;

    /**
     * Find an Answer entity by its name.
     *
     * @param pageable The name of the Answer entity.
     * @return An Optional containing the Output if found, or an empty Optional if not found.
     */
    Page<Answer> findAllByFormAct_SchoolYearAndFormAct_FormNameContainingIgnoreCase(Pageable pageable, SchoolYear schoolYear, String name) throws Exception;

    /**
     *  Find all answers before or after a given date.
     *
     *  @param date The date to compare.
     *  @param pageable The page of answers.
     *  @return A list of answers.
     *
     */
    Page<Answer> findAllByDateBetweenAndFormAct_SchoolYearId(LocalDateTime date, LocalDateTime date2, UUID schoolYear, Pageable pageable) throws Exception;

    /**
     * Deletes an answer forcefully by its ID.
     *
     * @param answerID The ID of the answer to be deleted.
     * @throws Exception If an error occurs during the deletion process.
     */
    @Modifying
    @Query(
            value = "DELETE FROM answer a WHERE a.id = ?1",
            nativeQuery = true)
    void forceDelete(UUID answerID) throws Exception;

    /**
     * Retrieves all answers paginated.
     *
     * @param pageable Pagination information.
     * @return A page of answers.
     * @throws Exception If an error occurs during the retrieval process.
     */
    Page<Answer> findAll(Pageable pageable) throws Exception;

    Page<Answer> findAllByFormAct(FormAct formAct, Pageable pageable) throws Exception;

    Page<Answer> findAllByFormAct_Form(Form form, Pageable pageable) throws Exception;

    Page<Answer> findAllByFormAct_SchoolYear(SchoolYear schoolYear, Pageable pageable) throws Exception;

}
