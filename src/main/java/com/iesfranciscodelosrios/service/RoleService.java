package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService implements iServices<Role> {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(UUID id) {
        try {
            Role result = roleRepository.findById(id).orElse(null);

            if (result != null) {
                logger.info("Buscando rol por ID '{}': {}", id, result);
            } else {
                logger.info("No se encontró ningún rol con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar rol por ID '{}': {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar rol por ID: " + e.getMessage());
        }
    }

    @Override
    public Role save(Role role) {
        try {
            logger.info("Guardando nuevo rol: {}", role);
            return roleRepository.save(role);
        } catch (Exception e) {
            logger.error("Error al guardar el rol: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el rol: " + e.getMessage());
        }
    }

    @Override
    public Role delete(Role role) {
        try {
            logger.info("Eliminando el rol: {}", role);
            roleRepository.delete(role);
            return role;
        } catch (Exception e) {
            logger.error("Error al eliminar el rol: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el rol: " + e.getMessage());
        }
    }

    public Role findByName(RoleType role) {
        try {
            Role result = roleRepository.findByRole(role)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando rol por nombre '{}': {}", role, result);
            } else {
                logger.info("No se encontró ningún rol con el nombre '{}'", role);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar rol por nombre '{}': {}", role, e.getMessage());
            throw new RuntimeException("Error al buscar rol por nombre: " + e.getMessage());
        }
    }
}
