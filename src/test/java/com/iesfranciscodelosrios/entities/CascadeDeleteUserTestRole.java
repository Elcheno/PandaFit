package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.RoleRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteUserTestRole {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    public void testCascadeDelete() {
        // Given
        Institution institution = createInstitutionForTest();
        Role role = createRoleForTest();

        UserEntity userEntity = UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .institution(institution)
                .build();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        userEntity.setRole(roleSet);

        // When
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // Then
        assertNotNull(savedUserEntity.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedUserEntity.getInstitution().getId(), "ID de la institución debería generarse después de guardar");
        assertEquals(1, savedUserEntity.getRole().size(), "Debería haber un Role guardado");

        // When (eliminar el usuario y verificar eliminación en cascada)
        userRepository.delete(savedUserEntity);

        // Then (verificar que el usuario y el Role se han eliminado)
        Optional<UserEntity> deletedUser = userRepository.findById(savedUserEntity.getId());
        assertFalse(deletedUser.isPresent(), "El usuario debería ser nulo después de eliminar");

        for (Role savedRole : savedUserEntity.getRole()) {
            Optional<Role> deletedRole = roleRepository.findById(savedRole.getId());
            assertFalse(deletedRole.isPresent(), "El Role debería ser nulo después de eliminar el usuario");
        }
    }

    private Institution createInstitutionForTest() {
        // Crea una institución para utilizarla en las pruebas
        return institutionRepository.save(Institution.builder()
                .name("Instituto de Prueba")
                .build());
    }

    private Role createRoleForTest() {
        // Crea un rol para utilizarlo en las pruebas
        return roleRepository.save(Role.builder()
                .role(RoleType.ADMIN)
                .build());
    }
}
