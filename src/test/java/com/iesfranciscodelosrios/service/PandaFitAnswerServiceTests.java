package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.temporal.ChronoUnit;
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
    private  LocalDateTime testDate = LocalDateTime.of(2024, 1, 22, 20, 29, 17, 835885);
    private Institution institution;
    private Set<Role> role = new HashSet<>();
    private UserEntity user;
    private Form form;
    private SchoolYear schoolYear;
    private FormAct formAct;
    private AnswerCreateDTO answerCreateDTO;
    private AnswerDeleteDTO answerDeleteDTO;

    public void beforeEach() {
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        institution = Institution.builder()
                .name("Institution1")
                .build();

        user = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

        // Guarda las entidades asociadas
        institutionRepository.save(institution);
        userRepository.save(user);

        // Crea una instancia de Form según tus necesidades
        form = Form.builder()
                .name("Form")
                .userOwner(user)
                .build();

        // Crea una instancia de SchoolYear según tus necesidades
        schoolYear = SchoolYear.builder()
                .name("SchoolYear")
                .institution(institution)
                .build();

        // Guarda las entidades asociadas
        formRepository.save(form);
        schoolYearRepository.save(schoolYear);

        // Given
        formAct = FormAct.builder()
                .startDate(testDate)
                .expirationDate(testDate.plusDays(7))
                .form(form)
                .schoolYear(schoolYear)
                .answersList(new HashSet<>())  // Inicializa la colección para evitar NPE
                .build();
        formActRepository.save(formAct);  // Guarda la instancia de FormAct

        answerCreateDTO = AnswerCreateDTO.builder()
                .id(UUID.randomUUID())
                .date(testDate)
                .uuid(UUID.randomUUID().toString())
                .build();

        answerDeleteDTO = AnswerDeleteDTO.builder()
                .date(testDate)
                .formAct(formAct)
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    @Test
    public void testSaveAnswer() {
        beforeEach();

        // When
        Answer savedAnswer = answerService.save(answerCreateDTO, formAct);

        // Then
        assertNotNull(savedAnswer.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedAnswer.getDate(), "La fecha no debería ser nula");
        //NanoOfSeconds en la fecha se cambia por culpa de assertEquals
        assertEquals(formAct, savedAnswer.getFormAct(), "El formulario debería ser igual");
        assertNotNull(savedAnswer.getUuid(), "El UUID no debería ser nulo");
    }

    @Test
    public void testLoadAnswerByDate() {
        beforeEach();

        // When
        Answer savedAnswer = answerService.save(answerCreateDTO, formAct);

        // When
        Answer loadedAnswer = answerService.loadAnswerByDate(testDate);

        // Then
        assertNotNull(loadedAnswer, "Debería encontrar una respuesta por fecha");
        assertEquals(testDate.truncatedTo(ChronoUnit.SECONDS), (loadedAnswer != null ? loadedAnswer.getDate().truncatedTo(ChronoUnit.SECONDS) : null), "La fecha debería ser igual");
        assertEquals(answerCreateDTO.getUuid(), loadedAnswer.getUuid(), "Los UUID deberían ser iguales");
    }

    @Test
    public void testDeleteAnswer() {
        beforeEach();

        // When
        Answer deletedAnswer = answerService.delete(answerDeleteDTO);

        // Then
        assertNotNull(deletedAnswer, "La respuesta eliminada no debería ser nula");
        assertEquals(answerDeleteDTO.getDate(), deletedAnswer.getDate(), "La fecha debería ser igual");
        assertFalse(answerRepository.findAnswerByDate(answerDeleteDTO.getDate()).isPresent(), "La respuesta debería ser eliminada de la base de datos");

    }
}
