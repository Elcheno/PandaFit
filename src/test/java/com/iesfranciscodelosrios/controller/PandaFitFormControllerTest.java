package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.service.FormService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FormController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PandaFitFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormService formService;

    @InjectMocks
    private FormController formController;

    private Form form;
    private UserEntity user;
    private FormResponseDTO formResponseDTO;
    private FormCreateDTO formCreateDTO;
    private FormUpdateDTO formUpdateDTO;
    private FormDeleteDTO formDeleteDTO;

    @BeforeEach
    void setUp() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().role(RoleType.USER).build());

        Institution institution = Institution.builder()
                .name("Institution")
                .build();

        user = UserEntity.builder()
                .email("email@example.com")
                .password("Password123!")
                .institution(institution)
                .role(roles)
                .build();

        UUID id = UUID.fromString("ef2479be-24a5-4699-8ac6-769c31b0608a");

        form = Form.builder()
                .id(id)
                .name("FormName")
                .description("Form Description")
                .userOwner(user)
                .build();


        formResponseDTO = FormResponseDTO.builder()
                .id(form.getId())
                .name(form.getName())
                .description(form.getDescription())
                .userOwner(form.getUserOwner())
                .formActList(form.getFormActList())
                .build();

        formCreateDTO = FormCreateDTO.builder()
                .name(form.getName())
                .description(form.getDescription())
                .userOwner(form.getUserOwner())
                .formActList(form.getFormActList())
                .build();

        formUpdateDTO = FormUpdateDTO.builder()
                .id(form.getId())
                .name("UpdatedFormName")
                .description("Updated Form Description")
                .userOwner(form.getUserOwner())
                .formActList(form.getFormActList())
                .build();

        formDeleteDTO = FormDeleteDTO.builder()
                .name(form.getName())
                .description(form.getDescription())
                .userOwner(form.getUserOwner())
                .formActList(form.getFormActList())
                .build();
    }

    @Test
    @Order(2)
    void getFormByName() throws Exception {
        when(formService.loadFormByName(any(String.class)))
                .thenReturn(form);

        ResultActions result = mockMvc.perform(get("/form/formulary/name")
                .param("name", form.getName())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(form.getId().toString()));
    }

    @Test
    @Order(3)
    void getFormById() throws Exception {
        when(formService.findById(any(UUID.class)))
                .thenReturn(form);

        ResultActions result = mockMvc.perform(get("/form/formulary/{id}", form.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(form.getId().toString()));
    }

    @Test
    @Order(1)
    void createForm() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonRequest = objectMapper.writeValueAsString(formCreateDTO);

        when(formService.save(any(Form.class)))
                .thenReturn(form);

        ResultActions result = mockMvc.perform(post("/form/formulary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
        System.out.println(form.toString());
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(form.getId().toString()))
                .andExpect(jsonPath("$.name").value(formCreateDTO.getName()))
                .andExpect(jsonPath("$.description").value(formCreateDTO.getDescription()));
    }

    @Test
    @Order(4)
    void updateForm() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonRequest = objectMapper.writeValueAsString(formUpdateDTO);
        System.out.println(formUpdateDTO);
        when(formService.save(any(Form.class)))
                .thenReturn(form);

        ResultActions result = mockMvc.perform(put("/form/formulary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(form.getId().toString()))
                .andExpect(jsonPath("$.name").value(formUpdateDTO.getName()))
                .andExpect(jsonPath("$.description").value(formUpdateDTO.getDescription()));
    }

    @Test
    @Order(5)
    void deleteForm() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonRequest = objectMapper.writeValueAsString(formDeleteDTO);

        when(formService.delete(any(Form.class)))
                .thenReturn(form);

        ResultActions result = mockMvc.perform(delete("/form/formulary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(form.getId().toString()));
    }

}
