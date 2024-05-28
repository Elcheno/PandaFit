package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.InputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestInput {

//    @Autowired
//    private InputRepository inputRepository;
//
//    @Autowired
//    private UserRepository userRepository;

    /*@Test
    @Transactional
    public void testCascadePersist() {
        // RESULTADO: Al guardar un input, el usuario debería ser persistido junto con el input por el CASCADE PERSIST
        // Given
        UserEntity userOwner = createUserForTest();

        // Asegúrate de que el propietario se asigna correctamente al inputCreateDTO
        Input input = Input.builder()
                .name("inputName")
                .description("Input Description")
                .validator("InputValidator")
                .userOwner(userOwner)
                .build();

        // When
        Input savedInput = inputRepository.save(input);

        // Then
        assertNotNull(savedInput.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedInput.getUserOwner().getId(), "ID del propietario debería generarse después de guardar");
        assertEquals("inputName", savedInput.getName(), "El nombre debe ser igual");
        assertEquals("Input Description", savedInput.getDescription(), "La descripción debe ser igual");
        assertEquals("InputValidator", savedInput.getValidator(), "El validador debe ser igual");
        assertEquals(userOwner.getId(), savedInput.getUserOwner().getId(), "El ID del propietario debería ser igual");
    }*/

//    private UserEntity createUserForTest() {
//        // Crea un usuario para utilizarlo en las pruebas
//        return userRepository.save(UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcdefg1!")
//                .build());
//    }
}
