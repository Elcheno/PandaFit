package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@SpringBootTest
public class PandaFitAnswerServiceTests {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private FormActRepository formActRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    public void testSaveAnswer() {

        Set<Role> role = new HashSet<>();
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        Institution institution = Institution.builder()
                .name("Institution1")
                .build();

        UserEntity user = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        // Guarda las entidades asociadas
        institutionRepository.save(institution);
        userRepository.save(user);

        // Crea una instancia de Form según tus necesidades
        Form form = Form.builder()
                .name("Form")
                .userOwner(user)
                .build();

        // Crea una instancia de SchoolYear según tus necesidades
        SchoolYear schoolYear = SchoolYear.builder()
                .name("SchoolYear")
                .institution(institution)
                .build();

        // Guarda las entidades asociadas
        formRepository.save(form);
        schoolYearRepository.save(schoolYear);

        // Given
        FormAct formAct = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .schoolYear(schoolYear)
                .answersList(new HashSet<>())  // Inicializa la colección para evitar NPE
                .build();
        formActRepository.save(formAct);  // Guarda la instancia de FormAct

        Answer answer = Answer.builder()
                .date(LocalDateTime.now())
                .formAct(formAct)
                .uuid(UUID.randomUUID().toString())
                .build();

        // When
        Answer savedAnswer = answerService.save(answer);

        // Then
        assertNotNull(savedAnswer.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedAnswer.getDate(), "La fecha no debería ser nula");
        assertEquals(formAct, savedAnswer.getFormAct(), "El formulario debería ser igual");
        assertNotNull(savedAnswer.getUuid(), "El UUID no debería ser nulo");
    }

    @Test
    public void testLoadAnswerByDate() {
        // Given
        LocalDateTime nuevaDate = LocalDateTime.of(2024, 1, 22, 20, 29, 17, 835885);

        Set<Role> role = new HashSet<>();
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        Institution institution = Institution.builder()
                .name("Institution")
                .build();

        UserEntity user = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        // Guarda las entidades asociadas
        institutionRepository.save(institution);
        userRepository.save(user);

        // Crea una instancia de Form según tus necesidades
        Form form = Form.builder()
                .name("Form")
                .userOwner(user)
                .build();

        // Crea una instancia de SchoolYear según tus necesidades
        SchoolYear schoolYear = SchoolYear.builder()
                .name("SchoolYear")
                .institution(institution)
                .build();

        // Guarda las entidades asociadas
        formRepository.save(form);
        schoolYearRepository.save(schoolYear);

        // Given
        FormAct formAct = FormAct.builder()
                .startDate(nuevaDate)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .schoolYear(schoolYear)
                .answersList(new HashSet<>())  // Inicializa la colección para evitar NPE
                .build();
        formActRepository.save(formAct);  // Guarda la instancia de FormAct

        Answer answer = Answer.builder()
                .date(nuevaDate)
                .formAct(formAct)
                .uuid(UUID.randomUUID().toString())
                .build();

        // When
        Answer savedAnswer = answerService.save(answer);

        // When
        Answer loadedAnswer = answerService.loadAnswerByDate(nuevaDate);

        // Then
        assertNotNull(loadedAnswer, "Debería encontrar una respuesta por fecha");
        assertEquals(nuevaDate.truncatedTo(ChronoUnit.SECONDS), (loadedAnswer != null ? loadedAnswer.getDate().truncatedTo(ChronoUnit.SECONDS) : null), "La fecha debería ser igual");
        assertEquals(answer.getUuid(), loadedAnswer.getUuid(), "Los UUID deberían ser iguales");
    }

    @Test
    public void testDeleteAnswer() {
        // Given
        LocalDateTime nuevaDate = LocalDateTime.of(2024, 1, 22, 20, 29, 17, 835885);

        Set<Role> role = new HashSet<>();
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        Institution institution = Institution.builder()
                .name("Institution")
                .build();

        UserEntity user = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        // Guarda las entidades asociadas
        institutionRepository.save(institution);
        userRepository.save(user);

        // Crea una instancia de Form según tus necesidades
        Form form = Form.builder()
                .name("Form")
                .userOwner(user)
                .build();

        // Crea una instancia de SchoolYear según tus necesidades
        SchoolYear schoolYear = SchoolYear.builder()
                .name("SchoolYear")
                .institution(institution)
                .build();

        // Guarda las entidades asociadas
        formRepository.save(form);
        schoolYearRepository.save(schoolYear);

        // Given
        FormAct formAct = FormAct.builder()
                .startDate(nuevaDate)
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .schoolYear(schoolYear)
                .answersList(new HashSet<>())  // Inicializa la colección para evitar NPE
                .build();
        formActRepository.save(formAct);  // Guarda la instancia de FormAct

        Answer answer = Answer.builder()
                .date(nuevaDate)
                .formAct(formAct)
                .uuid(UUID.randomUUID().toString())
                .build();

        // When
        Answer deletedAnswer = answerService.delete(answer);

        // Then
        assertNotNull(deletedAnswer, "La respuesta eliminada no debería ser nula");
        assertEquals(answer.getDate(), deletedAnswer.getDate(), "La fecha debería ser igual");
        assertFalse(answerRepository.findAnswerByDate(answer.getDate()).isPresent(), "La respuesta debería ser eliminada de la base de datos");

    }
}
