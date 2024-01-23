package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@SpringBootTest
public class PandafitSchoolYearServiceTests {

    @Autowired
    private SchoolYearService schoolYearService;

    @Autowired
    private InstitutionService institutionService;


    @Test
    public void testCreateSchoolYear() {
        schoolYearService.save(SchoolYear.builder()
                .name("1 ESO A")
                .institution(institutionService.findByName("global"))
                .build());
    }

    @Test
    public void testFindByIdSchoolYear() {
        System.out.println(schoolYearService.findById(UUID.fromString("dccdfd35-e2b1-4073-b931-06c929914041")));
    }

    @Test
    public void testFindByNameSchoolYear() {
        System.out.println(schoolYearService.findByNameAndInstitution("1 ESO C", institutionService.findByName("global")));
    }

    @Test
    public void testFindAllByInstitution() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SchoolYear> result = schoolYearService.findAllByInstitution(institutionService.findByName("global"), pageable);
        for(SchoolYear schoolYear : result) {
            System.out.println(schoolYear);
        }
    }

    @Test
    public void testFindByNameSchoolYearError() {
        System.out.println(schoolYearService.findByNameAndInstitution("1 ESO C", institutionService.findByName("error")));
    }

    @Test
    public void testDeleteSchoolYear() {
        schoolYearService.delete(schoolYearService.findByNameAndInstitution("1 ESO C", institutionService.findByName("global")));
    }
}
