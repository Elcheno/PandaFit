package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.service.InputService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestInput {
    @Autowired
    private InputService inputService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCascadePersist() {
        // RESULTADO: Al guardar un input el usuario debería ser persistido junto con el input por el CASCADE PERSIST
        // Given
        UserEntity userOwner = createUserForTest();

        // Asegúrate de que el propietario se asigna correctamente al inputCreateDTO
        InputCreateDTO inputCreateDTO = InputCreateDTO.builder()
                .name("inputName")
                .description("Input Description")
                .validator("InputValidator")
                .userOwnerId(userOwner.getId())
                .build();

        // When
        Input savedInput = inputService.save(inputCreateDTO);

        // Then
        assertNotNull(savedInput.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedInput.getUserOwner().getId(), "ID del propietario debería generarse después de guardar");
        assertEquals("inputName", savedInput.getName(), "El nombre debe ser igual");
        assertEquals("Input Description", savedInput.getDescription(), "La descripción debe ser igual");
        assertEquals("InputValidator", savedInput.getValidator(), "El validador debe ser igual");
        assertEquals(userOwner.getId(), savedInput.getUserOwner().getId(), "El ID del propietario debería ser igual");
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
