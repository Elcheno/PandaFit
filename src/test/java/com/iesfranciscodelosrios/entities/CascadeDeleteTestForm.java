package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteTestForm {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormActRepository formActRepository;

    @Test
    @Transactional
    public void testCascadeDelete() {
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
        Form savedForm = formRepository.save(form);

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

        // When (eliminar el formulario y verificar eliminación en cascada)
        formRepository.delete(savedForm);

        // Then (verificar que el formulario y los FormAct se han eliminado)
        Optional<Form> deletedForm = formRepository.findById(savedForm.getId());
        assertFalse(deletedForm.isPresent(), "El formulario debería ser nulo después de eliminar");

        for (FormAct savedFormAct : savedFormActList) {
            Optional<FormAct> deletedFormAct = formActRepository.findById(savedFormAct.getId());
            assertFalse(deletedFormAct.isPresent(), "El FormAct debería ser nulo después de eliminar el formulario");
        }
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userRepository.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
