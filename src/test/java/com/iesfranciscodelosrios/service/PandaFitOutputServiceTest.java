package com.iesfranciscodelosrios.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PandaFitOutputServiceTest {

    /*@Autowired
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
        Output output = Output.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwner(userOwner)
                .result("OutputResult")
                .build();

        // When
        Output savedOutput = outputService.save(output);

        // Then
        assertNotNull(savedOutput.getId(), "ID debería generarse después de guardar");
        assertEquals("outputName", savedOutput.getName(), "El nombre debe ser igual");
        assertEquals("Output Description", savedOutput.getDescription(), "La descripción debe ser igual");
        assertEquals("OutputFormula", savedOutput.getFormula(), "La fórmula debe ser igual");
        assertEquals(userOwner, savedOutput.getUserOwner(), "El propietario del usuario debe ser igual");
        assertEquals("OutputResult", savedOutput.getResult(), "El resultado debe ser igual");
    }

    @Test
    public void testFindByName() {
        // Given
        UserEntity userOwner = createUserForTest(); // Asegúrate de que createUserForTest() crea un UserEntity válido
        Output output = Output.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwner(userOwner)
                .result("OutputResult")
                .build();
        outputService.save(output);

        // When
        Output foundOutput = outputService.findByName("outputName");

        // Then
        assertNotNull(foundOutput, "Debería encontrar un output por nombre");
        assertEquals("outputName", foundOutput.getName(), "El nombre debe ser igual");
    }

    @Test
    public void testDeleteOutput() {
        // Given
        Output output = Output.builder()
                .name("outputName")
                .description("Output Description")
                .formula("OutputFormula")
                .userOwner(createUserForTest())
                .result("OutputResult")
                .build();
        outputService.save(output);

        // When
        Output deletedOutput = outputService.delete(output);

        // Then
        assertNotNull(deletedOutput, "El output eliminado no debería ser nulo");
        assertEquals("outputName", deletedOutput.getName(), "El nombre debe ser igual");
        assertTrue(outputRepository.findByName("outputName").isEmpty(), "El output debería ser eliminado de la base de datos");
    }

    private UserEntity createUserForTest() {
        // Crea una institución para utilizarla en las pruebas
        Institution institution = institutionService.save(Institution.builder()
                .name("TestInstitution")
                .build());

        // Define los roles según tu lógica, aquí asigno un role por defecto
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());

        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcd123!")
                .institution(institution)
                .role(roles)
                .build());
    }*/
}
