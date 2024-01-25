package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.service.OutputService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestOutput {
    //RESULTADO: Al guardar un output el usuario debería ser persistido junto con el input por el CASCADE PERSIST

    @Autowired
    private OutputService outputService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCascadePersist() {
        // Given
        UserEntity userOwner = createUserForTest();
        Output output = Output.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwner(userOwner)
                .build();

        // When
        Output savedOutput = outputService.save(output);

        // Then
        assertNotNull(savedOutput.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedOutput.getUserOwner().getId(), "ID del propietario debería generarse después de guardar");
        assertEquals("outputName", savedOutput.getName(), "El nombre debe ser igual");
        assertEquals("Output Description", savedOutput.getDescription(), "La descripción debe ser igual");
        assertEquals("OutputFormula", savedOutput.getFormula(), "La fórmula debe ser igual");
        assertEquals(userOwner.getId(), savedOutput.getUserOwner().getId(), "El ID del propietario debería ser igual");
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}

