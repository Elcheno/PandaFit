package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerResponseDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.service.AnswerService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    private FormAct formAct;
    private Answer answer;
    private AnswerResponseDTO answerResponseDTO;
    private AnswerDeleteDTO answerDeleteDTO;
    private AnswerCreateDTO answerCreateDTO;
    private SchoolYear schoolYear;
    private Form form;
    private UserEntity user;
    private Institution institution;
    private  LocalDateTime testDate = LocalDateTime.of(2024, 1, 22, 20, 29, 17, 835885);

    @BeforeEach
    void setUp() {
        Set<Role> role = new HashSet<>();
        role.add(Role.builder()
                .role(RoleType.USER)
                .build());

        institution = Institution.builder()
                .name("Institution")
                .build();

        user = UserEntity.builder()
                .email("email@example.com")
                .password("Pasword123!")
                .institution(institution)
                .role(role)
                .build();

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

        // Given
        formAct = FormAct.builder()
                .id(UUID.randomUUID())
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .form(form)
                .schoolYear(schoolYear)
                .answersList(new HashSet<>())  // Inicializa la colección para evitar NPE
                .build();

        answer = Answer.builder()
                .id(UUID.randomUUID())  // Simula un ID generado al guardar en la base de datos
                .date(testDate)
                .formAct(formAct)
                .uuid(UUID.randomUUID().toString())
                .build();

        answerResponseDTO = AnswerResponseDTO.builder()
                .id(answer.getId())
                .date(answer.getDate())
                .uuid(answer.getUuid())
                .build();

        answerCreateDTO = AnswerCreateDTO.builder()
                .id(answer.getId())
                .date(answer.getDate())
                .uuid(answer.getUuid())
                .build();

        answerDeleteDTO = AnswerDeleteDTO.builder()
                .date(answer.getDate())
                .formAct(answer.getFormAct())
                .uuid(answer.getUuid())
                .build();
    }

    @Test
    @Order(3)
    void getAnswerByDate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Convierte answerCreateDTO a JSON
        String jsonRequest = objectMapper.writeValueAsString(answerResponseDTO);

        // Mock de la respuesta esperada del servicio
        when(answerService.findById(any(UUID.class)))
                .thenReturn(answer);
        System.out.println("Mocked Answer: " + answer);

        // Llamada a la API y verificación de la respuesta
        ResultActions result = mockMvc.perform(get("/active/{idActive}/response/byDate/{date}",
                formAct.getId(), answer.getDate().toString())
                .param("idActive", formAct.getId().toString())
                .param("date", "2004")//SUSTITUIR answer.getDate().toString() por ENCODE url, buscar JAVA encode/decode url
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(answer.getId().toString()))
                .andDo(print());
    }

    @Test
    @Order(2)
    void getAnswerById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Convierte answerCreateDTO a JSON
        String jsonRequest = objectMapper.writeValueAsString(answerResponseDTO);

        // Mock de la respuesta esperada del servicio
        when(answerService.findById(any(UUID.class)))
                .thenReturn(answer);
        System.out.println("Mocked Answer: " + answer);

        // Llamada a la API y verificación de la respuesta
        ResultActions result = mockMvc.perform(get("/active/{idActive}/response/{id}", formAct.getId(), answer.getId())
                .param("idActive", formAct.getId().toString())
                .param("date", answerResponseDTO.getDate().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(answer.getId().toString()))
                .andDo(print());
    }

    @Test
    @Order(1)
    void createAnswerTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Convierte answerCreateDTO a JSON
        String jsonRequest = objectMapper.writeValueAsString(answerCreateDTO);

        // Configura el comportamiento del servicio mock
        when(answerService.save(any(AnswerCreateDTO.class))).thenReturn(answer);

        // Realiza la solicitud POST con el cuerpo JSON
        ResultActions result = mockMvc.perform(post("/active/{idActive}/response", formAct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(answer.getId().toString()))
                .andDo(print());
    }

    /*@Test
    @Order(4)
    void deleteAnswer() throws Exception {
        when(answerService.delete(any(Answer.class))).thenReturn(answer);

        ResultActions result = mockMvc.perform(delete("/active/{idActive}/response", formAct.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(answer.getId().toString()));
    }*/
}