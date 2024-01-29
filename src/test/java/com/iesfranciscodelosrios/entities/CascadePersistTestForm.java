package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.service.FormService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestForm {

    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCascadePersist() {
        // Given
        UserEntity userOwner = createUserForTest();
        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();

        FormAct formAct1 = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .build();

        FormAct formAct2 = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(14))
                .form(form)
                .build();

        Set<FormAct> formActList = new HashSet<>();
        formActList.add(formAct1);
        formActList.add(formAct2);

        form.setFormActList(formActList);

        // When
        Form savedForm = formService.save(form);

        // Then
        assertNotNull(savedForm.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedForm.getUserOwner().getId(), "ID del propietario debería generarse después de guardar");
        assertEquals("formName", savedForm.getName(), "El nombre debe ser igual");
        assertEquals("Form Description", savedForm.getDescription(), "La descripción debe ser igual");
        assertEquals(userOwner.getId(), savedForm.getUserOwner().getId(), "El ID del propietario debería ser igual");

        // Verificar que los FormAct también se hayan guardado en cascada
        Set<FormAct> savedFormActList = savedForm.getFormActList();
        assertNotNull(savedFormActList, "La lista de FormAct no debería ser nula");
        assertEquals(2, savedFormActList.size(), "Debería haber dos FormAct guardados");

        for (FormAct savedFormAct : savedFormActList) {
            assertNotNull(savedFormAct.getId(), "ID de FormAct debería generarse después de guardar");
            assertEquals(savedForm.getId(), savedFormAct.getForm().getId(), "ID del formulario en FormAct debería ser igual al ID del Formulario");
        }
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
