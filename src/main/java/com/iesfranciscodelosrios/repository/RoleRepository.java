package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.type.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByRole(RoleType role);
}
