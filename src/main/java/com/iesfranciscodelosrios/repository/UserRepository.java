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
    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable) throws Exception;

    Page<UserEntity> findAllByInstitution(Institution institution, Pageable pageable) throws Exception;

    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?1", nativeQuery = true)
    Page<UserEntity> findAllByRole(String role, Pageable pageable) throws Exception;

    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?2 AND u.institution_id = ?1", nativeQuery = true)
    Page<UserEntity> findAllByInstitutionAndRole(UUID institutionId, String role, Pageable pageable) throws Exception;

    @Query(value = "SELECT u.id, u.email, u.password, u.institution_id FROM users as u JOIN users_roles ON u.id = users_roles.user_id JOIN role as r ON r.id = users_roles.role_id WHERE r.role = ?2 AND u.institution_id = ?1", nativeQuery = true)
    Page<UserEntity> force(UUID institutionId, String role, Pageable pageable) throws Exception;

    @Modifying
    @Query(value = "DELETE FROM users as u WHERE u.id = ?1", nativeQuery = true)
    void forceDelete(UUID userID) throws Exception;
}
