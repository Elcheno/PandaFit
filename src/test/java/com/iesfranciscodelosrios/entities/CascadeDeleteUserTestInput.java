package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.InputRepository;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteUserTestInput {

    @Autowired
    private UserService userService;

    @Autowired
    private InputRepository inputRepository;

    @Test
    @Transactional
    public void testCascadeDeleteUser() {
        //RESULTADO: El usuario debería ser eliminado de la base de datos junto con sus inputs asociados
        // Given
        UserEntity userOwner = createUserForTest();

        // Create a list of inputs
        Set<Input> inputList = new HashSet<>();
        inputList.add(Input.builder().name("input1").userOwner(userOwner).build());
        inputList.add(Input.builder().name("input2").userOwner(userOwner).build());
        inputList.add(Input.builder().name("input3").userOwner(userOwner).build());

        // Set the input list to the user
        userOwner.setInputList(inputList);

        // Save the user
        userService.save(userOwner);

        // When
        // Delete the user
        userService.delete(userOwner);

        // Then
        // Check if the user has been deleted
        assertNull(userService.findById(userOwner.getId()), "El usuario debería ser eliminado de la base de datos");


        // Check if the associated inputs have been deleted
        for (Input input : inputList) {
            assertFalse(inputRepository.findById(input.getId()).isPresent(), "El input debería ser eliminado de la base de datos");
        }
    }

    private UserEntity createUserForTest() {
        // Implementar la creación de un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
