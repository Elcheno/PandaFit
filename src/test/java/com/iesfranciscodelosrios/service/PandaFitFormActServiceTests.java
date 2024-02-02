package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PandaFitFormActServiceTests {

    @Autowired
    private FormActService formActiveService;

    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    private UUID idFormAct;

    @Test
    @Order(1)
    public void testCreateInstitution() {

    }

    @Test
    @Order(2)
    public void testCreateUser() {
        Set<Role> role = new HashSet<>();
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        userService.save(UserEntity.builder()
                .email("ejemplo@example.com")
                .institution(institutionService.findByName("global"))
                .password("Ejemplo2&")
                .role(role)
                .build());
    }


    @Test
    @Order(3)
    public void testCreateForm() {

    }

    @Test
    @Order(4)
    public void testCreateFormActive() {
//        FormAct result = formActiveService.save(FormAct.builder()
//                .form(formService.loadFormByName("form"))
//                .build()
//        );
//        idFormAct = result.getId();

    }

    @Test
    @Order(5)
    public void testFindByForm() {
        FormAct result = formActiveService.findByForm(formService.loadFormByName("form"));
        System.out.println(result);
    }

    @Test
    @Order(6)
    public void testFindById() {
        FormAct result = formActiveService.findById(idFormAct);
        System.out.println(result);
    }

    @Test
    @Order(7)
    public void testDeleteFormActive() {

    }

    @Test
    @Order(8)
    public void testFindAll() {
        System.out.println(formActiveService.findAll());
    }
}
