package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.input.InputResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
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
public class PandaFitInputServiceTest {

//    @Autowired
//    private InputService inputService;
//
//    @Autowired
//    private InputRepository inputRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private InstitutionService institutionService;
//
//    @Test
//    @Transactional
//    public void testSaveInput() {
//        // Given
//        UserEntity userOwner = createUserForTest();
//        InputCreateDTO inputCreateDTO = InputCreateDTO.builder()
//                .name("inputName")
//                .description("Input Description")
//        //        .validator("InputValidator")
//                .userOwnerId(userOwner.getId())
//                .build();
//
//        // When
//        Input savedInput = inputService.save(inputCreateDTO);
//
//        // Then
//        assertNotNull(savedInput.getId(), "ID debería generarse después de guardar");
//        assertEquals("inputName", savedInput.getName(), "El nombre debe ser igual");
//        assertEquals("Input Description", savedInput.getDescription(), "La descripción debe ser igual");
//    //    assertEquals("InputValidator", savedInput.getValidator(), "El validador debe ser igual");
//        assertEquals(userOwner.getId(), savedInput.getUserOwner().getId(), "El ID del propietario debería ser igual");
//    }
//
//
//
//    @Test
//    @Transactional
//    public void testFindByName() {
//        // Given
//        UserEntity userOwner = createUserForTest();
//        InputCreateDTO inputCreateDTO = InputCreateDTO.builder()
//                .name("inputName")
//                .description("Input Description")
//        //        .validator("InputValidator")
//                .userOwnerId(userOwner.getId())
//                .build();
//
//        // Save the input
//        Input savedInput = inputService.save(inputCreateDTO);
//
//        // When
//        Input result = inputService.findByName("inputName");
//
//        // Then
//        assertNotNull(result, "Debería encontrar un input por nombre");
//        assertEquals("inputName", result.getName(), "El nombre debe ser igual");
//
//        System.out.println(result);
//    }
//
//
//
//    @Test
//    @Transactional
//    public void testDeleteInput() {
//        UserEntity userOwner = createUserForTest();
//        // Given
//        InputCreateDTO inputCreateDTO = InputCreateDTO.builder()
//                .name("inputName")
//                .description("Input Description")
//        //        .validator("InputValidator")
//                .userOwnerId(userOwner.getId())
//                .build();
//
//        Input savedInput = inputService.save(inputCreateDTO);
//
//        // When
//        InputDeleteDTO inputDeleteDTO = InputDeleteDTO.builder()
//                .id(savedInput.getId())
//        //        .name(savedInput.getName())
//                .build();
//        Input deletedInput = inputService.delete(inputDeleteDTO);
//
//        // Then
//        assertNotNull(deletedInput, "El input eliminado no debería ser nulo");
//        assertEquals("inputName", deletedInput.getName(), "El nombre debe ser igual");
//
//        // Cambia esta línea para manejar Optional correctamente
//        assertFalse(inputRepository.findById(savedInput.getId()).isPresent(), "El input debería ser eliminado de la base de datos");
//    }
//
//
//    private UserEntity createUserForTest() {
//        // Crea un usuario para utilizarlo en las pruebas
//        return userService.save(UserEntity.builder()
//                .email("testuser@example.com")
//                .institution(createInstitutionForTest())
//                .password("Abcdefg1!")
//                .role(createDefaultRoles())
//                .build());
//    }
//
//    public Institution createInstitutionForTest() {
//        // Crea una institución para utilizarla en las pruebas
//        InstitutionCreateDTO institutionCreateDTO = InstitutionCreateDTO.builder()
//                .name("TestInstitution")
//                .build();
//
//        return institutionService.save(institutionCreateDTO);
//    }
//
//
//    private Set<Role> createDefaultRoles() {
//        // Define los roles según tu lógica, aquí asigno un role por defecto
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.builder().role(RoleType.USER).build());
//        return roles;
//    }
}
