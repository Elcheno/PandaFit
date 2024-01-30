package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@SpringBootTest
public class PandafitInstitutionServiceTests {

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testCreateInstitution() {
        institutionService.save(InstitutionCreateDTO.builder()
                .name("global2")
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
    public void testFindByAll() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<Institution> result = institutionService.findAll(pageable);
        for(Institution institution : result) {
            System.out.println(institution);
        }
    }

    @Test
    public void testFindByNameError() {
        Institution result = institutionService.findByName("error");
        System.out.println(result);
    }

    /*@Test
    public void testDeleteInstitution() {
        institutionService.delete(institutionService.findByName("global"));
    }

    @Test
    public void testDeleteInstitutionError() {
        institutionService.delete(institutionService.findByName("error"));
    }*/
}
