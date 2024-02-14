package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActDeleteDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearCreateDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class PandaFitFormActServiceTests {

    @Autowired
    private FormActService formActService;

    @Autowired
    private FormActRepository formActRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolYearService schoolYearService;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    public void testSaveFormAct() {
        // Given
        UserEntity userOwner = createUserForTest();

        FormAct formAct = createFormActForTest();

        FormActCreateDTO formActCreateDTO = FormActCreateDTO.builder()
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .form(formAct.getForm())
                .schoolYear(formAct.getSchoolYear())
                .build();

        // When
        FormAct savedFormAct = formActService.save(formActCreateDTO);

        // Then
        assertNotNull(savedFormAct.getId(), "ID debería generarse después de guardar");
        assertNotNull(formActRepository.findById(savedFormAct.getId()), "No debería haber formularios de actividad en la base de datos、、");
    }

    @Test
    public void testFindById() {
        // Given
        FormAct savedFormAct = createFormActForTest();

        // When
        FormAct foundFormAct = formActService.findById(savedFormAct.getId());

        // Then
        assertNotNull(foundFormAct, "Debería encontrar un formulario de actividad por ID");
        assertEquals(savedFormAct.getId(), foundFormAct.getId(), "Los IDs deben ser iguales");
    }

    @Test
    public void testDeleteFormAct() {
        // Given
        FormAct savedFormAct = createFormActForTest();
        FormActDeleteDTO formActDeleteDTO = FormActDeleteDTO.builder()
                .id(savedFormAct.getId())
                .build();
        // When
        FormAct deletedFormAct = formActService.delete(formActDeleteDTO);

        // Then
        assertNotNull(deletedFormAct, "El formulario de actividad eliminado no debería ser nulo");
        assertEquals(savedFormAct.getId(), deletedFormAct.getId(), "Los IDs deben ser iguales");
        assertEquals(Optional.empty(), formActRepository.findById(savedFormAct.getId()), "No debería haber formularios de actividad en la base de datos después de eliminar");
    }

    private UserEntity createUserForTest() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());
        return userService.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcd123!")
                .institution(createInstitutionForTest())
                .role(roles)
                .build());
    }

    private Institution createInstitutionForTest() {
        return institutionRepository.save(Institution.builder()
                .name("Instituto de Prueba")
                .build());
    }

    private SchoolYear createSchoolYearForTest() {
        return schoolYearService.save(SchoolYearCreateDTO.builder()
                .name("2023-2024")
                .institutionId(createInstitutionForTest().getId())
                .build());
    }

    private Form createFormForTest() {
        UserEntity userOwner = createUserForTest();
        return formRepository.save(Form.builder()
                .name("Formulario de Prueba")
                .description("Descripción del formulario")
                .userOwner(userOwner)
                .build());
    }

    private FormAct createFormActForTest() {
        SchoolYear schoolYear = createSchoolYearForTest();

        FormActCreateDTO formActCreateDTO = FormActCreateDTO.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(createFormForTest())
                .schoolYear(schoolYear)
                .build();
        return formActService.save(formActCreateDTO);
    }
}
