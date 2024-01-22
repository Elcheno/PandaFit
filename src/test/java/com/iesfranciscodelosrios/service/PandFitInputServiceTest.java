package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.InputRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PandFitInputServiceTest {

    @Autowired
    private InputService inputService;

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testSaveInput() {
        // Given
        UserEntity userOwner = createUserForTest();
        Input input = Input.builder()
                .name("inputName")
                .description("Input Description")
                .validator("InputValidator")
                .userOwner(userOwner)
                .build();

        // When
        Input savedInput = inputService.save(input);

        // Then
        assertNotNull(savedInput.getId(), "ID debería generarse después de guardar");
        assertEquals("inputName", savedInput.getName(), "El nombre debe ser igual");
        assertEquals("Input Description", savedInput.getDescription(), "La descripción debe ser igual");
        assertEquals("InputValidator", savedInput.getValidator(), "El validador debe ser igual");
        assertEquals(userOwner, savedInput.getUserOwner(), "El propietario del usuario debe ser igual");
    }

    @Test
    @Transactional
    public void testFindByName() {
        // Given
        UserEntity userOwner = createUserForTest();
        Input input = Input.builder()
                .name("inputName")
                .description("Input Description")
                .validator("InputValidator")
                .userOwner(userOwner)
                .build();

        // Save the input
        Input savedInput = inputService.save(input);

        // When
        Input result = inputService.findByName("inputName");

        // Then
        assertNotNull(result, "Debería encontrar un input por nombre");
        assertEquals("inputName", result.getName(), "El nombre debe ser igual");

        System.out.println(result);
    }

    @Test
    @Transactional
    public void testDeleteInput() {
        UserEntity userOwner = createUserForTest();
        // Given
        Input input = Input.builder()
                .name("inputName")
                .description("Input Description")
                .validator("InputValidator")
                .userOwner(userOwner)
                .build();
        inputService.save(input);

        // When
        Input deletedInput = inputService.delete(input);

        // Then
        assertNotNull(deletedInput, "El input eliminado no debería ser nulo");
        assertEquals("inputName", deletedInput.getName(), "El nombre debe ser igual");

        // Cambia esta línea para manejar Optional correctamente
        assertFalse(inputRepository.findByName("inputName").isPresent(), "El input debería ser eliminado de la base de datos");
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .institution(createInstitutionForTest())
                .password("Abcdefg1!")
                .role(createDefaultRoles())
                .build());
    }

    public Institution createInstitutionForTest() {
        // Crea una institución para utilizarla en las pruebas
        return institutionService.save(Institution.builder()
                .name("TestInstitution")
                .build());
    }

    private Set<Role> createDefaultRoles() {
        // Define los roles según tu lógica, aquí asigno un role por defecto
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());
        return roles;
    }
}
