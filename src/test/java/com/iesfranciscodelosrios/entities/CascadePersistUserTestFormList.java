package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistUserTestFormList {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private InstitutionRepository institutionRepository;
//
//    @Autowired
//    private FormRepository formRepository;
//
//    @Test
//    @Transactional
//    public void testCascadePersist() {
//        // Given
//        Institution institution = createInstitutionForTest();
//        Form form = createFormForTest();
//
//        UserEntity userEntity = UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcdefg1!")
//                .institution(institution)
//                .build();
//
//        Set<Form> formSet = new HashSet<>();
//        formSet.add(form);
//        userEntity.setFormList(formSet);
//
//        // When
//        UserEntity savedUserEntity = userRepository.save(userEntity);
//
//        // Then
//        assertNotNull(savedUserEntity.getId(), "ID debería generarse después de guardar");
//        assertNotNull(savedUserEntity.getInstitution().getId(), "ID de la institución debería generarse después de guardar");
//        assertEquals(1, savedUserEntity.getFormList().size(), "Debería haber un Form guardado");
//
//        for (Form savedForm : savedUserEntity.getFormList()) {
//            assertNotNull(savedForm.getId(), "ID de Form debería generarse después de guardar");
//        }
//    }
//
//    private Institution createInstitutionForTest() {
//        // Crea una institución para utilizarla en las pruebas
//        return institutionRepository.save(Institution.builder()
//                .name("Instituto de Prueba")
//                .build());
//    }
//
//    private Form createFormForTest() {
//        // Crea un formulario para utilizarlo en las pruebas
//        return formRepository.save(Form.builder()
//                .name("Formulario de Prueba")
//                .description("Descripción del formulario")
//                .userOwner(null)  // Deberías establecer el usuario propietario aquí si es necesario
//                .build());
//    }
}
