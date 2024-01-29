package com.iesfranciscodelosrios.controller;

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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId) {
        UserEntity userEntity = userService.findById(UUID.fromString(userId));

        if (userEntity == null) return ResponseEntity.notFound().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .role(userEntity.getRole().stream().map(role -> RoleType.valueOf(role.getRole().name())).collect(Collectors.toSet()))
                .password(userEntity.getPassword())
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUser(@PageableDefault() Pageable pageable) {
        Page<UserEntity> result = userService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();
//        if (result.isEmpty()) return ResponseEntity.noContent().build();

        Page<UserResponseDTO> response = result.map(user -> UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().stream().map(role -> RoleType.valueOf(role.getRole().name())).collect(Collectors.toSet()))
                .password(user.getPassword())
//                .institution(user.getInstitution())
//                .formList(user.getFormList())
//                .outputList(user.getOutputList())
//                .inputList(user.getInputList())
                .build()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("{institutionId}/users/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllUserByInstitution(
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {

        Page<UserEntity> result = userService.findAllByInstitution(UUID.fromString(institutionId), pageable);

        if (result == null) return ResponseEntity.badRequest().build();
//        if (result.isEmpty()) return ResponseEntity.noContent().build();

        Page<UserResponseDTO> response = result.map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole().stream().map(role -> RoleType.valueOf(role.getRole().name())).collect(Collectors.toSet()))
                        .password(user.getPassword())
//                .institution(user.getInstitution())
//                .formList(user.getFormList())
//                .outputList(user.getOutputList())
//                .inputList(user.getInputList())
                        .build()
        );

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

        Page<UserResponseDTO> response = result.map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole().stream().map(roleType -> RoleType.valueOf(roleType.getRole().name())).collect(Collectors.toSet()))
                        .password(user.getPassword())
//                .institution(user.getInstitution())
//                .formList(user.getFormList())
//                .outputList(user.getOutputList())
//                .inputList(user.getInputList())
                        .build()
        );
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

        Page<UserResponseDTO> response = result.map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole().stream().map(roleType -> RoleType.valueOf(roleType.getRole().name())).collect(Collectors.toSet()))
                        .password(user.getPassword())
//                .institution(user.getInstitution())
//                .formList(user.getFormList())
//                .outputList(user.getOutputList())
//                .inputList(user.getInputList())
                        .build()
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {

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

        if (roles == null || roles.isEmpty() || institution == null) return ResponseEntity.badRequest().build();

        UserEntity userEntity = userService.save(UserEntity.builder()
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .role(roles)
                .institution(institution)
                .build());

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole().stream().map(role -> RoleType.valueOf(role.getRole().name())).collect(Collectors.toSet()))
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userService.save(UserEntity.builder()
                .id(userUpdateDTO.getId())
                .email(userUpdateDTO.getEmail())
                .password(userUpdateDTO.getPassword())
                .build());

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole().stream().map(roleType -> RoleType.valueOf(roleType.getRole().name())).collect(Collectors.toSet()))
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/users")
    public ResponseEntity<UserDeleteDTO> deleteUser(@RequestBody() UserDeleteDTO userDeleteDTO) {
        if (userDeleteDTO.getId() == null) return ResponseEntity.badRequest().build();
        userService.delete(userDeleteDTO.getId());
        return ResponseEntity.ok(userDeleteDTO);
    }

//    @DeleteMapping("/users")
//    public ResponseEntity<UserResponseDTO> deleteUser(@RequestBody() UserDeleteDTO userDeleteDTO) {
//        //UserEntity user = userService.findById(userDeleteDTO.getId());
//
//        //if (user == null) return ResponseEntity.badRequest().build();
//        // System.out.println("USUARIO");
//
//        UserEntity userEntity = userService.delete(userDeleteDTO.getId());
//
//        if (userEntity == null) return ResponseEntity.badRequest().build();
//
//        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
//                .id(userEntity.getId())
//                .email(userEntity.getEmail())
//                .password(userEntity.getPassword())
//                .role(userEntity.getRole())
//                .build();
//
//        return ResponseEntity.ok(userResponseDTO);
//    }

}
