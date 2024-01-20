package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.user.UserCreateDTO;
import com.iesfranciscodelosrios.model.dto.user.UserResponseDTO;
import com.iesfranciscodelosrios.model.dto.user.UserUpdateDTO;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/institution/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUserById(@RequestParam("id") String userId) {
        UserEntity userEntity = userService.findById(UUID.fromString(userId));

        if (userEntity == null) return ResponseEntity.notFound().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .institution(userEntity.getInstitution())
                .password(userEntity.getPassword())
                .formList(userEntity.getFormList())
                .outputList(userEntity.getOutputList())
                .inputList(userEntity.getInputList())
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserEntity userEntity = userService.save(UserEntity.builder()
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .role(userCreateDTO.getRole())
                .institution(userCreateDTO.getInstitution())
                .formList(userCreateDTO.getFormList())
                .inputList(userCreateDTO.getInputList())
                .outputList(userCreateDTO.getOutputList())
                .build());

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .institution(userEntity.getInstitution())
                .role(userEntity.getRole())
                .formList(userEntity.getFormList())
                .inputList(userEntity.getInputList())
                .outputList(userEntity.getOutputList())
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userService.save(UserEntity.builder()
                .id(userUpdateDTO.getId())
                .email(userUpdateDTO.getEmail())
                .password(userUpdateDTO.getPassword())
                .role(userUpdateDTO.getRole())
                .institution(userUpdateDTO.getInstitution())
                .formList(userUpdateDTO.getFormList())
                .inputList(userUpdateDTO.getInputList())
                .outputList(userUpdateDTO.getOutputList())
                .build());

        if (userEntity == null) return ResponseEntity.badRequest().build();

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .institution(userEntity.getInstitution())
                .role(userEntity.getRole())
                .formList(userEntity.getFormList())
                .inputList(userEntity.getInputList())
                .outputList(userEntity.getOutputList())
                .build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<UserResponseDTO> deleteUser(@RequestBody() UserResponseDTO userResponseDTO) {
        UserEntity userEntity = userService.delete(UserEntity.builder()
                        .id(userResponseDTO.getId())
                        .email(userResponseDTO.getEmail())
                        .password(userResponseDTO.getPassword())
                        .role(userResponseDTO.getRole())
                        .institution(userResponseDTO.getInstitution())
                        .formList(userResponseDTO.getFormList())
                        .inputList(userResponseDTO.getInputList())
                        .outputList(userResponseDTO.getOutputList())
                        .build());

        if (userEntity == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(userResponseDTO);
    }


}
