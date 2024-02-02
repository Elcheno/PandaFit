package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*@SpringBootTest
public class PandaFitFormServiceTests {
    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Test
    @Transactional
    public void testSaveForm() {

        UserEntity userOwner = createUserForTest();
        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();

        // Verificar si ya existe un formulario con el mismo nombre
        Form existingForm = formService.loadFormByName("formName");
        assertNull(existingForm, "Ya existe un formulario con el nombre formName");

        Form savedForm = formService.save(form);

        assertNotNull(savedForm.getId(), "ID debería generarse después de guardar");
        assertEquals("formName", savedForm.getName(), "El nombre debe ser igual");
        assertEquals("Form Description", savedForm.getDescription(), "La descripción debe ser igual");
        assertEquals(userOwner, savedForm.getUserOwner(), "El propietario del usuario debe ser igual");
    }

    @Test
    @Transactional
    public void testFindByName() {

        UserEntity userOwner = createUserForTest();
        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();

        Form savedForm = formService.save(form);
        Form result = formService.loadFormByName("formName");
        assertNotNull(result, "Debería encontrar un formulario por nombre");
        assertEquals("formName", result.getName(), "El nombre debe ser igual");
    }

    @Test
    @Transactional
    public void testFindById() {

        UserEntity userOwner = createUserForTest();
        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();

        Form savedForm = formService.save(form);

        Form result = formService.findById(savedForm.getId());

        assertNotNull(result, "Debería encontrar un formulario por ID");
        assertEquals(savedForm.getId(), result.getId(), "Los ID de formulario deben ser iguales");
        assertEquals("formName", result.getName(), "El nombre debe ser igual");
        assertEquals("Form Description", result.getDescription(), "La descripción debe ser igual");
        assertEquals(userOwner, result.getUserOwner(), "El propietario del usuario debe ser igual");
    }

    @Test
    @Transactional
    public void testDeleteForm() {
        UserEntity userOwner = createUserForTest();

        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();
        formService.save(form);

        Form deletedForm = formService.delete(form);
        assertNotNull(deletedForm, "El formulario eliminado no debería ser nulo");
        assertEquals("formName", deletedForm.getName(), "El nombre debe ser igual");
        Form loadedForm = formService.loadFormByName("formName");
        assertFalse(loadedForm != null, "El formulario debería ser eliminado de la base de datos");
    }

    private UserEntity createUserForTest() {
        // Verificar si la institución ya existe en la base de datos
        Institution existingInstitution = institutionService.findByName("TestInstitution");

        if (existingInstitution == null) {
            // Si no existe, crear una nueva institución
            existingInstitution = institutionService.save(Institution.builder()
                    .name("TestInstitution")
                    .build());
        }

        // Verificar si el usuario ya existe en la base de datos
        UserEntity existingUser = userService.findByEmail("testuser@example.com");

        if (existingUser == null) {
            // Si no existe, crear un nuevo usuario
            return userService.save(UserEntity.builder()
                    .email("testuser@example.com")
                    .institution(existingInstitution)
                    .password("Abcdefg1!")
                    .role(createDefaultRoles())
                    .build());
        } else {
            // Si el usuario ya existe
            return existingUser;
        }
    }


    private Set<Role> createDefaultRoles() {
        // Define los roles
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());
        return roles;
    }
}*/
