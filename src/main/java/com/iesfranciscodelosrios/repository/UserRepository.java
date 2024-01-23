package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAllByInstitution(Institution institution, Pageable pageable);

    Page<UserEntity> findAllByRole(RoleType role, Pageable pageable);

    Page<UserEntity> findAllByInstitutionAndRole(Institution institution, RoleType role, Pageable pageable);
}
