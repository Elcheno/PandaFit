package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId) {
        UserEntity userEntity = userService.findById(UUID.fromString(userId));

        if (userEntity == null) return ResponseEntity.notFound().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUser(@PageableDefault() Pageable pageable) {
        Page<UserEntity> result = userService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/email")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByEmailContaining(@PageableDefault(sort = "email") Pageable pageable, @RequestParam("email") String email) {
        Page<UserEntity> result = userService.findAllByEmailContaining(pageable, email);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{institutionId}/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByInstitution(
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {

        Page<UserEntity> result = userService.findAllByInstitution(UUID.fromString(institutionId), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UserResponseDTO> response = result.map(userService::mapToResponseDTO);

        return ResponseEntity.ok(response);
    }

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


    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        UserEntity userEntity = userService.save(userCreateDTO);

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userService.update(userUpdateDTO);

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = userService.mapToResponseDTO(userEntity);

        return ResponseEntity.ok(userResponseDTO);
    }

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
