package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.UserRepository;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistUserTestRole {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private InstitutionRepository institutionRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    @Transactional
//    public void testCascadePersist() {
//        // Given
//        Institution institution = createInstitutionForTest();
//        Role role = createRoleForTest();
//
//        UserEntity userEntity = UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcdefg1!")
//                .institution(institution)
//                .build();
//
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(role);
//        userEntity.setRole(roleSet);
//
//        // When
//        UserEntity savedUserEntity = userRepository.save(userEntity);
//
//        // Then
//        assertNotNull(savedUserEntity.getId(), "ID debería generarse después de guardar");
//        assertNotNull(savedUserEntity.getInstitution().getId(), "ID de la institución debería generarse después de guardar");
//        assertEquals(1, savedUserEntity.getRole().size(), "Debería haber un Role guardado");
//
//        for (Role savedRole : savedUserEntity.getRole()) {
//            assertNotNull(savedRole.getId(), "ID de Role debería generarse después de guardar");
//        }
//    }
//
//    private Institution createInstitutionForTest() {
//        // Crea una institución para utilizarla en las pruebas
//        return institutionRepository.save(Institution.builder()
//                .name("Instituto de Prueba")
//                .build());
//    }
//
//    private Role createRoleForTest() {
//        // Crea un rol para utilizarlo en las pruebas
//        return roleRepository.save(Role.builder()
//                .role(RoleType.ADMIN)
//                .build());
//    }
}
