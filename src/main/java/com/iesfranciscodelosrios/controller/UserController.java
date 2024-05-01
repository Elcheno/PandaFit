package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionByUserResponseDTO;
import com.iesfranciscodelosrios.model.dto.user.UserCreateDTO;
import com.iesfranciscodelosrios.model.dto.user.UserDeleteDTO;
import com.iesfranciscodelosrios.model.dto.user.UserResponseDTO;
import com.iesfranciscodelosrios.model.dto.user.UserUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.RoleService;
import com.iesfranciscodelosrios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("institution")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user details if found, otherwise returns a 404 Not Found response.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId) {
        UserEntity userEntity = userService.findById(UUID.fromString(userId));

        if (userEntity == null) return ResponseEntity.notFound().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);

        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Retrieves a page of all users.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of user details if found, otherwise returns a 400 Bad Request response.
     */
    @GetMapping("/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUser(@PageableDefault() Pageable pageable) {
        Page<UserEntity> result = userService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a page of users by email containing a specific keyword.
     *
     * @param pageable Pagination information.
     * @param email    The email keyword to search for.
     * @return ResponseEntity containing a page of user details if found, otherwise returns a 400 Bad Request response.
     */
    @GetMapping("/users/page/email")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByEmailContaining(@PageableDefault(sort = "email") Pageable pageable, @RequestParam("email") String email) {
        Page<UserEntity> result = userService.findAllByEmailContaining(pageable, email);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a page of users belonging to a specific institution.
     *
     * @param institutionId The ID of the institution.
     * @param pageable      Pagination information.
     * @return ResponseEntity containing a page of user details if found, otherwise returns a 400 Bad Request response.
     */
    @GetMapping("{institutionId}/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByInstitution(
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {

        Page<UserEntity> result = userService.findAllByInstitution(UUID.fromString(institutionId), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a page of users by their role.
     *
     * @param role     The role of the users.
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of user details if found, otherwise returns a 400 Bad Request response.
     */
    @GetMapping("/users/page/{role}")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByRole(
            @PathVariable("role") String role,
            @PageableDefault() Pageable pageable) {

        Page<UserEntity> result = userService.findAllByRole(
                role,
                pageable
        );

        if (result == null) return ResponseEntity.badRequest().build();
        if (result.isEmpty()) return ResponseEntity.noContent().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a page of users belonging to a specific institution and having a specific role.
     *
     * @param role          The role of the users.
     * @param institutionId The ID of the institution.
     * @param pageable      Pagination information.
     * @return ResponseEntity containing a page of user details if found, otherwise returns a 400 Bad Request response.
     */
    @GetMapping("{institutionId}/users/page/{role}")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByInstitutionAndRole(
            @PathVariable("role") String role,
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {

        Page<UserEntity> result = userService.findAllByInstitutionAndRole(
                UUID.fromString(institutionId),
                role,
                pageable
        );

        if (result == null) return ResponseEntity.badRequest().build();
        if (result.isEmpty()) return ResponseEntity.noContent().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}/institution")
    public ResponseEntity<InstitutionByUserResponseDTO> getUserInstitution(@PathVariable("id") String id) {
        InstitutionByUserResponseDTO institutionByUserResponseDTO = userService.getUserInstitution(UUID.fromString(id));
        if (institutionByUserResponseDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(institutionByUserResponseDTO);
    }

    /**
     * Creates a new user based on the provided user creation DTO.
     *
     * @param userCreateDTO The DTO containing user creation data.
     * @return ResponseEntity containing the created user details if successful, otherwise returns a 400 Bad Request response.
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        UserEntity userEntity = userService.save(userCreateDTO);

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Updates an existing user based on the provided user update DTO.
     *
     * @param userUpdateDTO The DTO containing user update data.
     * @return ResponseEntity containing the updated user details if successful, otherwise returns a 400 Bad Request response.
     */
    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userService.update(userUpdateDTO);

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);

        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Deletes a user based on the provided user deletion DTO.
     *
     * @param userDeleteDTO The DTO containing user deletion data.
     * @return ResponseEntity indicating whether the deletion was successful (true) or the user was not found (404).
     */
    @DeleteMapping("/users")
    public ResponseEntity<Boolean> deleteUser(@RequestBody() UserDeleteDTO userDeleteDTO) {
        boolean deleted = userService.delete(userDeleteDTO);

        if(deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
