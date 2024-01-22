package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PandaFitInstitutionServiceTests {

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testCreateInstitution() {
        institutionService.save(Institution.builder()
                .name("global")
                .build()
        );
    }

    @Test
    public void testFindById() {
        System.out.println(institutionService.findById(institutionService.findByName("global").getId()));
    }

    @Test
    public void testFindByName() {
        Institution result = institutionService.findByName("global");
        System.out.println(result);
    }

    @Test
    public void testFindByNameError() {
        Institution result = institutionService.findByName("error");
        System.out.println(result);
    }

    @Test
    public void testDeleteInstitution() {
        institutionService.delete(institutionService.findByName("global"));
    }

    @Test
    public void testDeleteInstitutionError() {
        institutionService.delete(institutionService.findByName("error"));
    }
}
