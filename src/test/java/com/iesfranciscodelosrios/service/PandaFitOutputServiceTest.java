package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.user.UserCreateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.OutputRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class PandaFitOutputServiceTest {

    @Autowired
    private OutputService outputService;

    @Autowired
    private OutputRepository outputRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testSaveOutput() {
        // Given
        UserEntity userOwner = createUserForTest();
        OutputCreateDTO outputCreateDTO = OutputCreateDTO.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwnerId(userOwner.getId())
                .build();

        // When
        Output savedOutput = outputService.save(outputCreateDTO);

        // Then
        assertNotNull(savedOutput.getId(), "ID debería generarse después de guardar");
        assertEquals("outputName", savedOutput.getName(), "El nombre debe ser igual");
        assertEquals("Output Description", savedOutput.getDescription(), "La descripción debe ser igual");
        assertEquals("OutputFormula", savedOutput.getFormula(), "La fórmula debe ser igual");
        assertEquals(userOwner, savedOutput.getUserOwner(), "El propietario del usuario debe ser igual");
    }



    @Test
    public void testFindByName() {
        // Given
        UserEntity userOwner = createUserForTest();
        OutputCreateDTO outputCreateDTO = OutputCreateDTO.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwnerId(userOwner.getId())  // Utiliza el ID del propietario
                .build();

        // Save the output using the service
        outputService.save(outputCreateDTO);

        // When
        Output foundOutput = outputService.findByName("outputName");

        // Then
        assertNotNull(foundOutput, "Debería encontrar un output por nombre");
        assertEquals("outputName", foundOutput.getName(), "El nombre debe ser igual");
    }


    @Test
    public void testDeleteOutput() {
        // Given
        UserEntity userOwner = createUserForTest();
        OutputCreateDTO outputCreateDTO = OutputCreateDTO.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwnerId(userOwner.getId())  // Utiliza el ID del propietario
                .build();

        // Save the output using the service
        Output savedOutput = outputService.save(outputCreateDTO);

        OutputDeleteDTO outputDeleteDTO = OutputDeleteDTO.builder()
                .id(savedOutput.getId())
                .build();

        // When
        Output deletedOutput = outputService.delete(outputDeleteDTO);

        // Then
        assertNotNull(deletedOutput, "El output eliminado no debería ser nulo");
        assertEquals("outputName", deletedOutput.getName(), "El nombre debe ser igual");
        assertTrue(outputRepository.findByName("outputName").isEmpty(), "El output debería ser eliminado de la base de datos");
    }


    private UserEntity createUserForTest() {
        // Crea una institución para utilizarla en las pruebas
        InstitutionCreateDTO institutionCreateDTO = InstitutionCreateDTO.builder()
                .name("TestInstitution")
                .build();

        Institution institution = institutionService.save(institutionCreateDTO);

        // Define los roles según tu lógica, aquí asigno un role por defecto
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());

        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserCreateDTO.builder()
                .email("testuser@example.com")
                .password("Abcd123!")
                .build());
    }

}
