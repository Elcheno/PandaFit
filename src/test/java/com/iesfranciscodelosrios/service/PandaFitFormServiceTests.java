package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PandaFitFormServiceTests {
    @Autowired
    private FormService formService;
    @Autowired
    private UserService userService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private FormActService formActService;
    private UserEntity userOwner;
    private Form form;
    private FormAct formAct;
    private LocalDateTime testDate = LocalDateTime.of(2024, 1, 22, 20, 29, 17, 835885);
    private FormCreateDTO formCreateDTOEmpty;
    private FormCreateDTO formCreateDTO;
    private Set<Role> role = new HashSet<>();
    private Institution institution;
    private FormDeleteDTO formDeleteDTO;
    private FormUpdateDTO formUpdateDTO;
    private FormAct formAct1;
    private FormAct formAct2;


    private void beforeEach() {
        HashSet<FormAct> formsActEmpty = new HashSet<>();
        HashSet<FormAct> formsAct = new HashSet<>();

        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        userOwner = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        form = Form.builder()
                .id(UUID.randomUUID())
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .formActList(formsAct)
                .build();

        formAct1 = FormAct.builder()
                .startDate(testDate)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .build();

        formAct2 = FormAct.builder()
                .startDate(testDate)
                .expirationDate(LocalDateTime.now().plusDays(14)) // Diferente vencimiento para distinguir
                .form(form)
                .build();

        formsAct.add(formAct1);
        formsAct.add(formAct2);


        formCreateDTO = FormCreateDTO.builder()
                .name("formName")
                .description("Form create description")
                .userId(userOwner.getId())
                .build();

        formDeleteDTO = FormDeleteDTO.builder()
                .id(form.getId())
                .build();

        formUpdateDTO = FormUpdateDTO.builder()
                .id(form.getId())
                .name("formName")
                .description("Form update description")
                .userId(userOwner.getId())
                .formActList(formsActEmpty)
                .build();

        institution = institutionService.save(InstitutionCreateDTO.builder()
                .name("Institution1")
                .build());

        userService.save(userOwner);
    }

    @Test
    @Transactional
    public void testSaveFormAct() {
        beforeEach();

        // Verificar si ya existe un formulario con el mismo nombre
        Form existingForm = formService.loadFormByName("formName");
        assertNull(existingForm, "Ya existe un formulario con el nombre formName");

        Form savedForm = formService.save(formCreateDTOEmpty);

        assertNotNull(savedForm.getId(), "ID debería generarse después de guardar");
        assertEquals("formName", savedForm.getName(), "El nombre debe ser igual");
        assertEquals("Form create description", savedForm.getDescription(), "La descripción debe ser igual");
        assertEquals(userOwner, savedForm.getUserOwner(), "El propietario del usuario debe ser igual");
        System.out.println(savedForm);
    }

    @Test
    @Transactional
    public void testFindByName() {
        beforeEach();

        Form result = formService.loadFormByName(form.getName());
        assertNotNull(result, "Debería encontrar un formulario por nombre");
        assertEquals("formName", result.getName(), "El nombre debe ser igual");
    }

    @Test
    @Transactional
    public void testFindById() {
        beforeEach();

        Form result = formService.findById(form.getId());

        assertNotNull(result, "Debería encontrar un formulario por ID");
        assertEquals(form.getId(), result.getId(), "Los ID de formulario deben ser iguales");
        assertEquals("formName", result.getName(), "El nombre debe ser igual");
        assertEquals("Form Description", result.getDescription(), "La descripción debe ser igual");
        assertEquals(userOwner, result.getUserOwner(), "El propietario del usuario debe ser igual");
    }

    @Test
    @Transactional
    public void testDeleteForm() {
        beforeEach();

        Form deletedForm = formService.delete(formDeleteDTO);
        assertNotNull(deletedForm, "El formulario eliminado no debería ser nulo");
        assertEquals("formName", deletedForm.getName(), "El nombre debe ser igual");
        Form loadedForm = formService.loadFormByName("formName");
        assertFalse(loadedForm != null, "El formulario debería ser eliminado de la base de datos");
    }
}
