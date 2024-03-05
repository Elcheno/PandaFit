package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActDeleteDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearCreateDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.service.FormActService;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import com.iesfranciscodelosrios.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FormActControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FormActService formActService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SchoolYearService schoolYearService;
//
//    @Autowired
//    private FormRepository formRepository;
//
//    @Autowired
//    private InstitutionService institutionService;
//
//    @Test
//    public void testGetFormActById() throws Exception {
//        // Arrange
//        UUID formActId = UUID.randomUUID();
//        FormAct formAct = FormAct.builder()
//                .id(formActId)
//                .startDate(LocalDateTime.now())
//                .expirationDate(LocalDateTime.now().plusDays(7))
//                .form(createFormForTest())
//                .build();
//
//        when(formActService.findById(formActId)).thenReturn(formAct);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/active/{id}", formActId.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(formActId.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Form Act"));
//    }
//
//    @Test
//    public void testGetAllFormsAct() throws Exception {
//        // Arrange
//        Pageable mockPageable = PageRequest.of(0, 20);
//        Page<FormAct> mockPage = Mockito.mock(Page.class);
//
//        SchoolYear schoolYear = createSchoolYearForTest();
//        schoolYear.setId(UUID.randomUUID());
//
//        List<FormAct> mockFormActs = Arrays.asList(
//                FormAct.builder()
//                        .form(createFormForTest())
//                        .schoolYear(schoolYear)
//                        .startDate(LocalDateTime.now())
//                        .expirationDate(LocalDateTime.now().plusDays(7))
//                        .build(),
//                FormAct.builder()
//                        .form(createFormForTest())
//                        .schoolYear(schoolYear)
//                        .startDate(LocalDateTime.now())
//                        .expirationDate(LocalDateTime.now().plusDays(5))
//                        .build());
//
//        Page<FormActResponseDTO> expectedPage = convertPageToFormActResponseDTOPage(mockPage, mockFormActs);
//
//        when(formActService.findAll(mockPageable)).thenReturn(mockPage);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/active/page")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(mockFormActs.get(0).getId().toString()));
//    }
//
//    // Helper method to convert Page<FormAct> to Page<FormActResponseDTO>
//    private Page<FormActResponseDTO> convertPageToFormActResponseDTOPage(Page<FormAct> page, List<FormAct> formActs) {
//        return page.map(formAct -> FormActResponseDTO.builder()
//                .id(formAct.getId())
//                .startDate(formAct.getStartDate())
//                .expirationDate(formAct.getExpirationDate())
//                .build());
//    }
//
//    @Test
//    public void testCreateFormAct() throws Exception {
//        // Arrange
//        FormActCreateDTO createDTO = FormActCreateDTO.builder()
//                .startDate(LocalDateTime.now())
//                .expirationDate(LocalDateTime.now().plusDays(7))
//                .form(createFormForTest())
//                .build();
//
//        FormAct savedFormAct = FormAct.builder()
//                .startDate(createDTO.getStartDate())
//                .expirationDate(createDTO.getExpirationDate())
//                .form(createDTO.getForm())
//                .build();
//
//        FormActResponseDTO expectedResponse = FormActResponseDTO.builder()
//                .id(savedFormAct.getId())
//                .startDate(savedFormAct.getStartDate())
//                .expirationDate(savedFormAct.getExpirationDate())
//                .build();
//
//        when(formActService.save(createDTO)).thenReturn(savedFormAct);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/active")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createDTO)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Form Act"));
//    }
//
//    @Test
//    public void testDeleteFormActById() throws Exception {
//        // Arrange
//        UUID formActId = UUID.randomUUID();
//        FormActDeleteDTO mockFormAct = FormActDeleteDTO.builder()
//                .id(formActId)
//                .build();
//
//        FormAct formAct = FormAct.builder()
//                .id(mockFormAct.getId())
//                .form(createFormForTest())
//                .build();
//
//        when(formActService.findById(formActId)).thenReturn(formAct);
//
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.delete("/active/{id}", formActId.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    private UserEntity createUserForTest() {
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.builder().role(RoleType.USER).build());
//        return userService.save(UserEntity.builder()
//                .email("testuser@example.com")
//                .password("Abcd123!")
//                .institution(createInstitutionForTest())
//                .role(roles)
//                .build());
//    }
//
//    private Institution createInstitutionForTest() {
//        return institutionService.save(InstitutionCreateDTO.builder()
//                .name("Instituto de Prueba")
//                .build());
//    }
//
//    private SchoolYear createSchoolYearForTest() {
//
//        Institution institution = createInstitutionForTest();
//
//        SchoolYearCreateDTO schoolYearCreateDTO = SchoolYearCreateDTO.builder()
//                .name("2023-2024")
//                .institutionId(institution.getId())
//                .build();
//
//        return schoolYearService.save(schoolYearCreateDTO);
//    }
//
//    private Form createFormForTest() {
//        UserEntity userOwner = createUserForTest();
//        return formRepository.save(Form.builder()
//                .name("Formulario de Prueba")
//                .description("Descripción del formulario")
//                .userOwner(userOwner)
//                .build());
//    }
//
//    private FormAct createFormActForTest() {
//        SchoolYear schoolYear = createSchoolYearForTest();
//
//        FormActCreateDTO formActCreateDTO = FormActCreateDTO.builder()
//                .startDate(LocalDateTime.now())
//                .expirationDate(LocalDateTime.now().plusDays(7))
//                .form(createFormForTest())
//                .schoolYear(schoolYear)
//                .build();
//        return formActService.save(formActCreateDTO);
//    }
}