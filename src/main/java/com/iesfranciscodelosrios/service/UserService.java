package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.user.UserCreateDTO;
import com.iesfranciscodelosrios.model.dto.user.UserDeleteDTO;
import com.iesfranciscodelosrios.model.dto.user.UserResponseDTO;
import com.iesfranciscodelosrios.model.dto.user.UserUpdateDTO;
import com.iesfranciscodelosrios.model.entity.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private RoleService roleService;

    @Transactional
    public UserEntity save(UserCreateDTO userCreateDTO) {
        try {
            logger.info("Guardando nuevo usuario: {}", userCreateDTO);

            Set<Role> roles = userCreateDTO.getRoles().stream().map((String tryRole) -> {
                        RoleType roleType;
                        try {
                            roleType = RoleType.valueOf(tryRole);
                        } catch (IllegalArgumentException e) {
                            ErrorResponseException error = new ErrorResponseException(HttpStatus.BAD_REQUEST);
                            error.setDetail("Invalid role type");
                            throw error;
                        }
                        Role role = roleService.findByName(roleType);
                        if (role == null) return Role.builder()
                                .role(roleType)
                                .build();
                        return role;
                    })
                    .collect(Collectors.toSet());

            Institution institution = institutionService.findById(userCreateDTO.getInstitutionId());

            UserEntity user = UserEntity.builder()
                    .email(userCreateDTO.getEmail())
                    .password(userCreateDTO.getPassword())
                    .role(roles)
                    .institution(institution)
                    .build();

            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error al guardar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage());
        }
    }

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

    @Transactional
    public UserEntity updateUUID(UserEntity user, String uuid) {
        try {
            UserEntity userToUptdate = findById(user.getId());

            UserEntity userEntity = UserEntity.builder()
                    .id(userToUptdate.getId())
                    .email(userToUptdate.getEmail())
                    .password(userToUptdate.getPassword())
                    .institution(userToUptdate.getInstitution())
                    .role(userToUptdate.getRole())
                    .password(userToUptdate.getPassword())
                    .uuid(uuid)
                    .build();

            logger.info("Actualizando el usuario con ID {}: {}", userToUptdate.getId(), userEntity);

            return userRepository.save(userEntity);
        } catch (Exception e) {
            logger.error("Error al actualizar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    public UserEntity delete(UserEntity user) {
        // El método original no implementa la eliminación, así que no hay operación de eliminación aquí
        return null;
    }

    @Transactional
    public boolean delete(UserDeleteDTO userDeleteDTO) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userDeleteDTO.getId());
            if (userEntityOptional.isPresent()) {
                logger.info("Eliminando el usuario con ID: {}: {}", userDeleteDTO.getId(), userEntityOptional.get());
                userRepository.forceDelete(userEntityOptional.get().getId());
                return true;
            }
            logger.error("No se pudo eliminar el usuario con ID '{}' : {}", userDeleteDTO.getId(), userEntityOptional.get());
            return false;
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el usuario.\n" + e.getMessage());
        }
    }

    @Transactional
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

    public Page<UserEntity> findAllByEmailContaining(Pageable pageable, String email) {
        try {
            Page<UserEntity> result = userRepository.findAllByEmailContainingIgnoreCase(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10
                    ), email
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

    public UserResponseDTO mapToResponseDTO(UserEntity user) {
        try {
            logger.info("Creando la response de {}", user);

            Set<UUID> inputUidList = new HashSet<>();
            Set<UUID> outputUidList = new HashSet<>();
            Set<UUID> formUidList = new HashSet<>();

            if(user.getInputList() != null){
                for (Input input : user.getInputList()) {
                    inputUidList.add(input.getId());
                }
            }

            if(user.getOutputList() != null){
                for (Output output : user.getOutputList()) {
                    outputUidList.add(output.getId());
                }
            }

            if(user.getFormList() != null){
                for (Form form : user.getFormList()) {
                    formUidList.add(form.getId());
                }
            }

            return UserResponseDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .institutionId(user.getInstitution().getId())
                    .inputsId(inputUidList)
                    .outputsId(outputUidList)
                    .formsId(formUidList)
                    .role(user.getRole().stream().map(role -> RoleType.valueOf(role.getRole().name())).collect(Collectors.toSet()))
                    .build();
        } catch (Exception e) {
            logger.error("Error al crear la response {}", user);
            throw new RuntimeException("Error al crear la response " + e.getMessage());
        }
    }
}
