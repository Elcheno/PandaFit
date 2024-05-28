package com.iesfranciscodelosrios.entities;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CascadePersistTestFormAct {

//    @Autowired
//    private FormActRepository formActRepository;
//
//    @Autowired
//    private FormRepository formRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @Transactional
//    public void testCascadePersist() {
//        // Given
//        UserEntity userOwner = createUserForTest();
//        Form form = Form.builder()
//                .name("formName")
//                .description("Form Description")
//                .userOwner(userOwner)
//                .build();
//
//        FormAct formAct = FormAct.builder()
//                .startDate(LocalDateTime.now())
//                .expirationDate(LocalDateTime.now().plusDays(7))
//                .form(form)
//                .build();
//
//        Answer answer1 = Answer.builder()
//                .date(LocalDateTime.now())
//                .formAct(formAct)
//                .uuid("uuid1")
//                .build();
//
//        Answer answer2 = Answer.builder()
//                .date(LocalDateTime.now())
//                .formAct(formAct)
//                .uuid("uuid2")
//                .build();
//
//        Set<Answer> answerList = new HashSet<>();
//        answerList.add(answer1);
//        answerList.add(answer2);
//
//        formAct.setAnswersList(answerList);
//
//        // When
//        Form savedForm = formRepository.save(form);
//        FormAct savedFormAct = formActRepository.save(formAct);
//
//        // Then
//        assertNotNull(savedFormAct.getId(), "ID debería generarse después de guardar");
//        assertNotNull(savedFormAct.getForm().getId(), "ID del formulario debería generarse después de guardar");
//        assertEquals(2, savedFormAct.getAnswersList().size(), "Debería haber dos respuestas guardadas");
//
//        for (Answer savedAnswer : savedFormAct.getAnswersList()) {
//            assertNotNull(savedAnswer.getId(), "ID de Answer debería generarse después de guardar");
//            assertEquals(savedFormAct.getId(), savedAnswer.getFormAct().getId(), "ID del formulario en Answer debería ser igual al ID del Formulario");
//        }
//    }
//
//    private UserEntity createUserForTest() {
//        // Crea un usuario para utilizarlo en las pruebas
//        return userRepository.save(UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcdefg1!")
//                .build());
//    }
}
