package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.SchoolYearRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteInstitutionTestSchoolYearList {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Test
    @Transactional
    public void testCascadeDeleteSchoolYearList() {
        // Given
        Institution institution = Institution.builder()
                .name("Instituto de Prueba")
                .build();

        SchoolYear schoolYear1 = SchoolYear.builder()
                .name("2023-2024")
                .institution(institution)
                .build();

        SchoolYear schoolYear2 = SchoolYear.builder()
                .name("2024-2025")
                .institution(institution)
                .build();

        schoolYearRepository.save(schoolYear1);
        schoolYearRepository.save(schoolYear2);

        Set<SchoolYear> schoolYearList = new HashSet<>();
        schoolYearList.add(schoolYear1);
        schoolYearList.add(schoolYear2);

        institution.setSchoolYearList(schoolYearList);

        // When
        Institution savedInstitution = institutionRepository.save(institution);

        // Then
        assertNotNull(savedInstitution.getId(), "ID debería generarse después de guardar");
        assertEquals(2, savedInstitution.getSchoolYearList().size(), "Debería haber dos SchoolYear guardados");

        // When (eliminar la institución y verificar eliminación en cascada)
        institutionRepository.delete(savedInstitution);

        // Then (verificar que la institución y los SchoolYear se han eliminado)
        assertNull(institutionRepository.findById(savedInstitution.getId()).orElse(null), "La institución debería ser nula después de eliminar");

        for (SchoolYear savedSchoolYear : savedInstitution.getSchoolYearList()) {
            assertNull(schoolYearRepository.findById(savedSchoolYear.getId()).orElse(null), "El SchoolYear debería ser nulo después de eliminar la institución");
        }
    }
}
