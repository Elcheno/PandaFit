package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class PandafitUserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @Test
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
    public void testFindByEmail() {
        UserEntity result = userService.findByEmail("ejemplo@example.com");
        System.out.println(result);
    }

    @Test
    public void testFindByEmailError() {
        UserEntity result = userService.findByEmail("error");
        System.out.println(result);
    }

    @Test
    public void testDeleteUser() {
        userService.delete(userService.findByEmail("ejemplo@example.com"));
    }
}
