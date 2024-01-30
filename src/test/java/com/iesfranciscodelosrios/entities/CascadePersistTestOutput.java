package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.OutputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestOutput {

    @Autowired
    private OutputRepository outputRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testCascadePersist() {
        // RESULTADO: Al guardar un output, el usuario debería ser persistido junto con el input por el CASCADE PERSIST
        // Given
        UserEntity userOwner = createUserForTest();
        Output output = Output.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
        //        .userOwnerId(userOwner.getId())
                .result("Some Result")
                .build();


        // When
        Output savedOutput = outputRepository.save(output);

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
        return userRepository.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
