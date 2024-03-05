package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.OutputRepository;
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
public class CascadeDeleteUserTestOutput {
    //RESULTADO: El usuario debería ser eliminado de la base de datos junto con sus outputs asociados

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private OutputRepository outputRepository;
//
//    @Test
//    @Transactional
//    public void testCascadeDeleteUser() {
//        // RESULTADO: El usuario debería ser eliminado de la base de datos junto con sus outputs asociados
//        // Given
//        UserEntity userOwner = createUserForTest();
//
//        // Create a list of outputs
//        Set<Output> outputList = new HashSet<>();
//        outputList.add(Output.builder().name("output1").userOwner(userOwner).build());
//        outputList.add(Output.builder().name("output2").userOwner(userOwner).build());
//        outputList.add(Output.builder().name("output3").userOwner(userOwner).build());
//
//        // Set the output list to the user
//        userOwner.setOutputList(outputList);
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
//        // Check if the associated outputs have been deleted
//        for (Output output : outputList) {
//            Optional<Output> deletedOutput = outputRepository.findById(output.getId());
//            assertFalse(deletedOutput.isPresent(), "El output debería ser eliminado de la base de datos");
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
