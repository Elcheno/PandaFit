package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PandafitSchoolYearServiceTests {

    @Autowired
    private SchoolYearService schoolYearService;

    @Autowired
    private InstitutionService institutionService;


    @Test
    public void testCreateSchoolYear() {
        schoolYearService.save(SchoolYear.builder()
                .name("1o ESO C")
                .institution(institutionService.findByName("global"))
                .build());
    }

    @Test
    public void testFindByNameSchoolYear() {
        System.out.println(schoolYearService.findByName("1o ESO C"));
    }

    @Test
    public void testFindByNameSchoolYearError() {
        System.out.println(schoolYearService.findByName("error"));
    }

    @Test
    public void testDeleteSchoolYear() {
        schoolYearService.delete(schoolYearService.findByName("1o ESO C"));
    }
}
