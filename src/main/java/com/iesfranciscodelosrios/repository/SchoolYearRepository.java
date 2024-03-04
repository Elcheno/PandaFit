package com.iesfranciscodelosrios.repository;


import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, UUID> {

    /**
     * Find a SchoolYear entity by its name and associated Institution.
     *
     * @param name        The name of the SchoolYear.
     * @param institution The Institution associated with the SchoolYear.
     * @return An Optional containing the SchoolYear if found, or an empty Optional if not found.
     */
    Optional<SchoolYear> findByNameAndAndInstitution(String name, Institution institution);

    /**
     * Retrieve all SchoolYear entities associated with a specific Institution.
     *
     * @param institution The Institution associated with the SchoolYears.
     * @param pageable    Pageable object for pagination.
     * @return A Page containing SchoolYear entities.
     * @throws Exception If an error occurs during retrieval.
     */
    Page<SchoolYear> findAllByInstitution(Institution institution, Pageable pageable) throws Exception;

    /**
     * Delete a SchoolYear entity by its ID.
     * @param sachoolYearID The ID of the SchoolYear to delete.
     * @throws Exception If an error occurs during deletion.
     */
    @Modifying
    @Query(value = "DELETE FROM school_year as u WHERE u.id = ?1", nativeQuery = true)
    void forceDelete(UUID sachoolYearID) throws Exception;

    /**
     * Retrieve all SchoolYear entities whose name contains the specified string, ignoring case.
     *
     * @param pageable Pageable object for pagination.
     * @param name     The string to search for in SchoolYear names.
     * @return A Page containing SchoolYear entities.
     * @throws Exception If an error occurs during retrieval.
     */
    Page<SchoolYear> findAllByNameContainingIgnoreCase(Pageable pageable, String name) throws Exception;
}
