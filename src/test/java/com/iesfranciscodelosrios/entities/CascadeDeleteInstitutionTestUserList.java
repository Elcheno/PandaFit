package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteInstitutionTestUserList {

//    @Autowired
//    private InstitutionRepository institutionRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @Transactional
//    public void testCascadeDeleteUserList() {
//        // Given
//        Institution institution = Institution.builder()
//                .name("Instituto de Prueba")
//                .build();
//
//        UserEntity user1 = UserEntity.builder()
//                .email("user1@example.com")
//                .password("Abcdefg1!")
//                .institution(institution)
//                .build();
//
//        UserEntity user2 = UserEntity.builder()
//                .email("user2@example.com")
//                .password("Abcdefg1!")
//                .institution(institution)
//                .build();
//
//        Set<UserEntity> userList = new HashSet<>();
//        userList.add(user1);
//        userList.add(user2);
//
//        institution.setUserList(userList);
//
//        // When
//        Institution savedInstitution = institutionRepository.save(institution);
//
//        // Then
//        assertNotNull(savedInstitution.getId(), "ID debería generarse después de guardar");
//        assertEquals(2, savedInstitution.getUserList().size(), "Debería haber dos usuarios guardados");
//
//        // When (eliminar la institución y verificar eliminación en cascada)
//        institutionRepository.delete(savedInstitution);
//
//        // Then (verificar que la institución y los usuarios se han eliminado)
//        assertNull(institutionRepository.findById(savedInstitution.getId()).orElse(null), "La institución debería ser nula después de eliminar");
//
//        for (UserEntity savedUser : savedInstitution.getUserList()) {
//            assertNull(userRepository.findById(savedUser.getId()).orElse(null), "El usuario debería ser nulo después de eliminar la institución");
//        }
//    }
}
