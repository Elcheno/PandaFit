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
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        institution = institutionService.save(InstitutionCreateDTO.builder()
                .name("Institution1")
                .build());

        userOwner = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        userService.save(userOwner);

        // Ahora crearemos los FormAct y el Form después de guardar el usuario propietario
        HashSet<FormAct> formsAct = new HashSet<>();

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
                .expirationDate(LocalDateTime.now().plusDays(14))
                .form(form)
                .build();

        formsAct.add(formAct1);
        formsAct.add(formAct2);

        HashSet<UUID> formActUidList = new HashSet<>(Arrays.asList(formAct1.getId(), formAct2.getId()));

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
                .name("formUpdated")
                .description("Form updated description")
                .userId(userOwner.getId())
                .formActUidList(formActUidList)
                .build();
    }

    @Test
    @Transactional
    public void testSaveForm() {
        beforeEach();

        // Verificar si ya existe un formulario con el mismo nombre
        Form existingForm = formService.loadFormByName("formName");
        assertNull(existingForm, "Ya existe un formulario con el nombre formName");

        System.out.println("Dirección de memoria de userOwner antes de guardar: " + System.identityHashCode(userOwner));
        Form savedForm = formService.save(formCreateDTO);
        System.out.println("Dirección de memoria de userOwner después de guardar: " + System.identityHashCode(userOwner));

        assertNotNull(savedForm.getId(), "ID debería generarse después de guardar");
        assertEquals("formName", savedForm.getName(), "El nombre debe ser igual");
        assertEquals("Form create description", savedForm.getDescription(), "La descripción debe ser igual");
//      Por algún motivo da error aunque es exactamnete el mismo objeto
        assertEquals(userOwner, savedForm.getUserOwner(), "El propietario del usuario debe ser igual");
        System.out.println(savedForm);
    }

    @Test
    @Transactional
    public void testUpdate() {
        beforeEach();

        // Guardar un formulario inicial
        Form initialForm = formService.save(formCreateDTO);
        // Verificar que el formulario se guardó correctamente
        assertNotNull(initialForm.getId(), "El ID del formulario no debería ser nulo después de guardar");

        // Verificar que el formulario existe antes de la actualización
        Form existingForm = formService.loadFormByName("formName");
        assertNotNull(existingForm, "El formulario a actualizar no existe");

        // Ejecutar la actualización del formulario
        Form updatedForm = formService.update(formUpdateDTO);

        // Verificar que la actualización se realizó correctamente
        assertNotNull(updatedForm.getId(), "El ID del formulario actualizado no debería ser nulo después de la actualización");
        assertEquals(initialForm.getId(), updatedForm.getId(), "El ID del formulario actualizado debería ser el mismo que el original");
        assertEquals(formUpdateDTO.getName(), updatedForm.getName(), "El nombre del formulario actualizado debe ser igual al nombre especificado en formUpdateDTO");
        assertEquals(formUpdateDTO.getDescription(), updatedForm.getDescription(), "La descripción del formulario actualizado debe ser igual a la descripción especificada en formUpdateDTO");
        assertEquals(userOwner, updatedForm.getUserOwner(), "El propietario del usuario del formulario actualizado debe ser igual al propietario especificado en formUpdateDTO");
        assertEquals(formUpdateDTO.getFormActUidList().size(), updatedForm.getFormActList().size(), "La cantidad de FormAct asociados al formulario actualizado debe ser igual a la cantidad especificada en formUpdateDTO");

        // Verificar que los FormAct asociados al formulario actualizado sean los mismos que los especificados en formUpdateDTO
        for (FormAct formAct : updatedForm.getFormActList()) {
            assertTrue(formUpdateDTO.getFormActUidList().contains(formAct.getId()), "El formulario actualizado debería contener los mismos FormAct que los especificados en formUpdateDTO");
        }
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
