package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.FormActService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import com.iesfranciscodelosrios.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteTestSchoolYear {

    @Autowired
    private SchoolYearService schoolYearService;

    @Autowired
    private FormActService formActService;

    @Autowired
    private InstitutionService institutionService;

    @Test
    @Transactional
    public void testCascadeDelete() {
        // Given
        Institution institution = createInstitutionForTest();
        SchoolYear schoolYear = SchoolYear.builder()
                .name("2023-2024")
                .institution(institution)
                .build();

        FormAct formAct1 = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
        //        .schoolYear(schoolYear)
                .build();

        FormAct formAct2 = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(14))
        //        .schoolYear(schoolYear)
                .build();

        Set<FormAct> formActList = new HashSet<>();
        formActList.add(formAct1);
        formActList.add(formAct2);

        schoolYear.setFormActList(formActList);

        // When
        SchoolYear savedSchoolYear = schoolYearService.save(schoolYear);

        // Then
        assertNotNull(savedSchoolYear.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedSchoolYear.getInstitution().getId(), "ID de la institución debería generarse después de guardar");
        assertEquals(2, savedSchoolYear.getFormActList().size(), "Debería haber dos FormAct guardados");

        // When (eliminar el año escolar y verificar eliminación en cascada)
        schoolYearService.delete(savedSchoolYear);

        // Then (verificar que el año escolar y los FormAct se han eliminado)
        assertNull(schoolYearService.findById(savedSchoolYear.getId()), "El año escolar debería ser nulo después de eliminar");
        for (FormAct savedFormAct : savedSchoolYear.getFormActList()) {
            assertNull(formActService.findById(savedFormAct.getId()), "El FormAct debería ser nulo después de eliminar el año escolar");
        }
    }

    private Institution createInstitutionForTest() {
        // Crea una institución para utilizarla en las pruebas
        return institutionService.save(Institution.builder()
                .name("Instituto de Prueba")
                .build());
    }
}