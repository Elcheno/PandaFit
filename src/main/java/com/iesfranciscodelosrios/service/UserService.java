package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.user.UserUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.apache.catalina.User;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements iServices<UserEntity> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionService institutionService;

    @Override
    public UserEntity save(UserEntity user) {
        try {
            logger.info("Guardando nuevo usuario: {}", user);
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error al guardar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public UserEntity findById(UUID id) {
        try {
            UserEntity result = userRepository.findById(id)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando usuario por ID '{}': {}", id, result);
            } else {
                logger.info("No se encontró ningún usuario con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar usuario por ID '{}': {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage());
        }
    }

    @Transactional
    public UserEntity update(UserUpdateDTO userUpdateDTO) {
        try {
            UserEntity userToUptdate = findById(userUpdateDTO.getId());

            UserEntity userEntity = UserEntity.builder()
                    .id(userUpdateDTO.getId())
                    .email(userUpdateDTO.getEmail())
                    .password(userUpdateDTO.getPassword())
                    .institution(userToUptdate.getInstitution())
                    .role(userToUptdate.getRole())
                    .build();

            logger.info("Actualizando el usuario con ID {}: {}", userUpdateDTO.getId(), userEntity);

            return userRepository.save(userEntity);
        } catch (Exception e) {
            logger.error("Error al actualizar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public UserEntity delete(UserEntity user) {
        // El método original no implementa la eliminación, así que no hay operación de eliminación aquí
        return null;
    }

    @Transactional
    public UserEntity delete(UUID id) {
        if (id == null) return null;
        try {
            userRepository.forceDelete(id);
            logger.info("Eliminando el usuario con ID: {}", id);
        } catch (GenericJDBCException a) {
            logger.error("Error al forzar la eliminación del usuario con ID '{}': {}", id, a.getMessage());
            throw new RuntimeException("Error al forzar la eliminación del usuario: " + a.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }

        return null;
    }

    public UserEntity findByEmail(String email) {
        try {
            UserEntity result = userRepository.findByEmail(email)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando usuario por email '{}': {}", email, result);
            } else {
                logger.info("No se encontró ningún usuario con el email '{}'", email);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar usuario por email '{}': {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar usuario por email: " + e.getMessage());
        }
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        try {
            Page<UserEntity> result = userRepository.findAll(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,

                            pageable.getSort()
                    )
            );

            logger.info("Buscando todos los usuarios paginados: {}", result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los usuarios paginados: {}", e.getMessage());
            throw new RuntimeException("Error al buscar todos los usuarios paginados: " + e.getMessage());
        }
    }

    public Page<UserEntity> findAllByInstitution(UUID institutionId, Pageable pageable) {
        Institution institution = institutionService.findById(institutionId);
        if (institution == null) return null;

        try {
            Page<UserEntity> result = userRepository.findAllByInstitution(
                    institution,
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,

                            pageable.getSort()
                    )
            );

            logger.info("Buscando todos los usuarios de la institución '{}' paginados: {}", institution, result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los usuarios de la institución '{}' paginados: {}", institution, e.getMessage());
            throw new RuntimeException("Error al buscar todos los usuarios de la institución paginados: " + e.getMessage());
        }
    }

    public Page<UserEntity> findAllByRole(String role, Pageable pageable) {
        RoleType rol;
        try {
            rol = RoleType.valueOf(role);
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            Page<UserEntity> result = userRepository.findAllByRole(
                    role,
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,

                            pageable.getSort()
                    )
            );

            logger.info("Buscando todos los usuarios por rol '{}' paginados: {}", role, result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los usuarios por rol '{}' paginados: {}", role, e.getMessage());
            throw new RuntimeException("Error al buscar todos los usuarios por rol paginados: " + e.getMessage());
        }
    }

    public Page<UserEntity> findAllByInstitutionAndRole(UUID institutionId, String role, Pageable pageable) {
        Institution institution = institutionService.findById(institutionId);
        RoleType rol;
        try {
            rol = RoleType.valueOf(role);
        } catch (IllegalArgumentException e) {
            return null;
        }

        if (institution == null) return null;

        try {
            Page<UserEntity> result = userRepository.findAllByInstitutionAndRole(
                    institution.getId(),
                    role,
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,

                            pageable.getSort()
                    )
            );

            logger.info("Buscando todos los usuarios de la institución '{}' por rol '{}' paginados: {}", institution, role, result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los usuarios de la institución '{}' por rol '{}' paginados: {}", institution, role, e.getMessage());
            throw new RuntimeException("Error al buscar todos los usuarios de la institución por rol paginados: " + e.getMessage());
        }
    }
}
