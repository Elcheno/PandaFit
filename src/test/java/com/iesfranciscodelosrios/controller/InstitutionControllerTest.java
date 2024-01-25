package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionDeleteDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class InstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstitutionService institutionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetInstitutionById() throws Exception {
        // Arrange
        UUID institutionId = UUID.randomUUID();
        Institution institution = new Institution();
        institution.setId(institutionId);
        institution.setName("Test Institution");

        when(institutionService.findById(institutionId)).thenReturn(institution);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/institution/{id}", institutionId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(institutionId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Institution"));
    }

    /*@Test
    public void testGetAllInstitutions() throws Exception {
        // Arrange
        UUID institutionId = UUID.randomUUID();
        Institution institution = new Institution();
        institution.setId(institutionId);
        institution.setName("Test Institution");

        Page<Institution> institutionPage = new PageImpl<>(Collections.singletonList(institution));

        when(institutionService.findAll(any(Pageable.class))).thenReturn(institutionPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/institution/page")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(institutionId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test Institution"));
    }*/

    @Test
    public void testCreateInstitution() throws Exception {
        // Arrange
        InstitutionCreateDTO createDTO = new InstitutionCreateDTO();
        createDTO.setName("New Institution");

        Institution institution = new Institution();
        institution.setId(UUID.randomUUID());
        institution.setName("New Institution");

        when(institutionService.save(any(Institution.class))).thenReturn(institution);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Institution"));
    }

    @Test
    public void testUpdateInstitution() throws Exception {
        // Arrange
        InstitutionUpdateDTO updateDTO = new InstitutionUpdateDTO();
        updateDTO.setId(UUID.randomUUID());
        updateDTO.setName("Updated Institution");

        Institution institution = new Institution();
        institution.setId(updateDTO.getId());
        institution.setName(updateDTO.getName());

        when(institutionService.save(any(Institution.class))).thenReturn(institution);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updateDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Institution"));
    }

    @Test
    public void testDeleteInstitution() throws Exception {
        // Arrange
        InstitutionDeleteDTO deleteDTO = new InstitutionDeleteDTO();
        deleteDTO.setId(UUID.randomUUID());
        deleteDTO.setName("Delete Institution");

        Institution institution = new Institution();
        institution.setId(deleteDTO.getId());
        institution.setName(deleteDTO.getName());

        when(institutionService.delete(any(Institution.class))).thenReturn(institution);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/institution")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deleteDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Delete Institution"));
    }
}
