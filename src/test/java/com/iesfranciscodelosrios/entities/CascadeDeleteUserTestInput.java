package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.InputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteUserTestInput {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private InputRepository inputRepository;
//
//    @Test
//    @Transactional
//    public void testCascadeDeleteUser() {
//        //RESULTADO: El usuario debería ser eliminado de la base de datos junto con sus inputs asociados
//        // Given
//        UserEntity userOwner = createUserForTest();
//
//        // Create a list of inputs
//        Set<Input> inputList = new HashSet<>();
//        inputList.add(Input.builder().name("input1").userOwner(userOwner).build());
//        inputList.add(Input.builder().name("input2").userOwner(userOwner).build());
//        inputList.add(Input.builder().name("input3").userOwner(userOwner).build());
//
//        // Set the input list to the user
//        userOwner.setInputList(inputList);
//
//        // Save the user
//        userRepository.save(userOwner);
//
//        // When
//        // Delete the user
//        userRepository.delete(userOwner);
//
//        // Then
//        // Check if the user has been deleted
//        Optional<UserEntity> deletedUser = userRepository.findById(userOwner.getId());
//        assertFalse(deletedUser.isPresent(), "El usuario debería ser eliminado de la base de datos");
//
//        // Check if the associated inputs have been deleted
//        for (Input input : inputList) {
//            Optional<Input> deletedInput = inputRepository.findById(input.getId());
//            assertFalse(deletedInput.isPresent(), "El input debería ser eliminado de la base de datos");
//        }
//    }
//
//    private UserEntity createUserForTest() {
//        // Implementar la creación de un usuario para utilizarlo en las pruebas
//        return userRepository.save(UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcdefg1!")
//                .build());
//    }
}
