package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadeDeleteTestFormAct {

    @Autowired
    private FormActRepository formActRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @Transactional
    public void testCascadeDelete() {
        // Given
        UserEntity userOwner = createUserForTest();
        Form form = Form.builder()
                .name("formName")
                .description("Form Description")
                .userOwner(userOwner)
                .build();

        FormAct formAct = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .build();

        Answer answer1 = Answer.builder()
                .date(LocalDateTime.now())
                .formAct(formAct)
                .uuid("uuid1")
                .build();

        Answer answer2 = Answer.builder()
                .date(LocalDateTime.now())
                .formAct(formAct)
                .uuid("uuid2")
                .build();

        Set<Answer> answerList = new HashSet<>();
        answerList.add(answer1);
        answerList.add(answer2);

        formAct.setAnswersList(answerList);

        // When
        Form savedForm = formRepository.save(form);
        FormAct savedFormAct = formActRepository.save(formAct);

        // Then
        assertNotNull(savedFormAct.getId(), "ID debería generarse después de guardar");
        assertNotNull(savedFormAct.getForm().getId(), "ID del formulario debería generarse después de guardar");
        assertEquals(2, savedFormAct.getAnswersList().size(), "Debería haber dos respuestas guardadas");

        // When (eliminar el formulario de actividad y verificar eliminación en cascada)
        formActRepository.delete(savedFormAct);

        // Then (verificar que el formulario de actividad y las respuestas se han eliminado)
        Optional<FormAct> deletedFormAct = formActRepository.findById(savedFormAct.getId());
        assertFalse(deletedFormAct.isPresent(), "El formulario de actividad debería ser nulo después de eliminar");

        for (Answer savedAnswer : savedFormAct.getAnswersList()) {
            Optional<Answer> deletedAnswer = answerRepository.findById(savedAnswer.getId());
            assertFalse(deletedAnswer.isPresent(), "La respuesta debería ser nula después de eliminar el formulario de actividad");
        }
    }

    private UserEntity createUserForTest() {
        // Crea un usuario para utilizarlo en las pruebas
        return userRepository.save(UserEntity.builder()
                .email("testuser@example.com")
                .password("Abcdefg1!")
                .build());
    }
}
