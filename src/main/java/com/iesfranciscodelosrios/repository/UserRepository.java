package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    /**
     * Find a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Retrieve all users.
     *
     * @param pageable Pageable object for pagination.
     * @return A Page containing UserEntity objects.
     * @throws Exception If an error occurs during retrieval.
     */
    Page<UserEntity> findAll(Pageable pageable) throws Exception;

    /**
     * Retrieve all users associated with a specific institution.
     *
     * @param institution The institution associated with the users.
     * @param pageable    Pageable object for pagination.
     * @return A Page containing UserEntity objects.
     * @throws Exception If an error occurs during retrieval.
     */
    Page<UserEntity> findAllByInstitution(Institution institution, Pageable pageable) throws Exception;

    /**
     * Retrieves all users with a specific role.
     *
     * @param role     The role to filter users by.
     * @param pageable Pagination information.
     * @return A page of UserEntity objects.
     * @throws Exception If an error occurs during execution.
     */
    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?1", nativeQuery = true)
    Page<UserEntity> findAllByRole(String role, Pageable pageable) throws Exception;

    /**
     * Retrieves all users with a specific role and associated with a specific institution.
     *
     * @param institutionId The ID of the institution to filter users by.
     * @param role          The role to filter users by.
     * @param pageable      Pagination information.
     * @return A page of UserEntity objects.
     * @throws Exception If an error occurs during execution.
     */
    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?2 AND u.institution_id = ?1", nativeQuery = true)
    Page<UserEntity> findAllByInstitutionAndRole(UUID institutionId, String role, Pageable pageable) throws Exception;

    /**
     * A method named 'force' which seems to retrieve users by role and institution ID, but the purpose is not clear from the name or signature.
     *
     * @param institutionId The ID of the institution to filter users by.
     * @param role          The role to filter users by.
     * @param pageable      Pagination information.
     * @return A page of UserEntity objects.
     * @throws Exception If an error occurs during execution.
     */
    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?2 AND u.institution_id = ?1", nativeQuery = true)
    Page<UserEntity> force(UUID institutionId, String role, Pageable pageable) throws Exception;

    /**
     * Retrieves all users containing a specified email address, ignoring case.
     *
     * @param pageable Pagination information.
     * @param email    The email address to filter users by.
     * @return A page of UserEntity objects.
     * @throws Exception If an error occurs during execution.
     */
    Page<UserEntity> findAllByEmailContainingIgnoreCase(Pageable pageable, String email) throws Exception;

    /**
     * Delete a user by their ID.
     *
     * @param userID The ID of the user to delete.
     * @throws Exception If an error occurs during deletion.
     */
    @Modifying
    @Query(value = "DELETE FROM users as u WHERE u.id = ?1", nativeQuery = true)
    void forceDelete(UUID userID) throws Exception;
}
