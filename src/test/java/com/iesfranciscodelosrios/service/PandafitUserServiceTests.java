package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.user.UserCreateDTO;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
                .build()
        );

        userService.save(UserCreateDTO.builder()
                .email("user2@example.com")
                .password("Ejemplo1&")
                .build()
        );

        for(int i = 25; i < 46; i++) {
            role.add(Role.builder()
                    .role(RoleType.USER)
                    .build()
            );

            UserEntity user = userService.save(UserCreateDTO.builder()
                    .email("user" + i + "@example.com")
                    .password("Ejemplo1&")
                    .build()
            );
        }
    }
    
    @Test
    public void testFindByUser() {
        System.out.println(userService.findById(userService.findByEmail("ejemplo@example.com").getId()));
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

    }

    @Test
    public void testDeleteUserError() {
        System.out.println(userService.delete(userService.findByEmail("error")));
    }

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> result = userService.findAll(pageable);
        for(UserEntity user : result) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindAllByInstitution() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> result = userService.findAllByInstitution(institutionService.findByName("global2").getId(), pageable);
        for(UserEntity user : result) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindAllByRole() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> result = userService.findAllByRole("USER", pageable);
        for(UserEntity user : result) {
            System.out.println(user);
        }
    }

    @Test
    public void testFindAllByInstitutionAndRole() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> result = userService.findAllByInstitutionAndRole(institutionService.findByName("global1").getId(),"USER", pageable);
        for(UserEntity user : result) {
            System.out.println(user);
        }
    }
}
