package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputResponseDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.service.OutputService;
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
public class OutputControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OutputService outputService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetOutputById() throws Exception {
        // Arrange
        UUID outputId = UUID.randomUUID();
        Output output = new Output();
        output.setId(outputId);
        output.setName("Test Output");

        when(outputService.findById(outputId)).thenReturn(output);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/form/output/{id}", outputId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(outputId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Output"));
    }

    @Test
    public void testGetAllOutputs() throws Exception {
        // Arrange
        UUID outputId = UUID.randomUUID();
        Output output = new Output();
        output.setId(outputId);
        output.setName("Test Output");

        Page<Output> outputPage = new PageImpl<>(Collections.singletonList(output));

        when(outputService.findAll(any(Pageable.class))).thenReturn(outputPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/form/outputs/page")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(outputId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test Output"));
    }

    @Test
    public void testCreateOutput() throws Exception {
        // Arrange
        OutputCreateDTO createDTO = new OutputCreateDTO();
        createDTO.setName("New Output");

        Output output = new Output();
        output.setId(UUID.randomUUID());
        output.setName("New Output");

        when(outputService.save(any(OutputCreateDTO.class))).thenReturn(output);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/form/output")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Output"));
    }

    @Test
    public void testUpdateOutput() throws Exception {
        // Arrange
        OutputUpdateDTO updateDTO = new OutputUpdateDTO();
        updateDTO.setId(UUID.randomUUID());
        updateDTO.setName("Updated Output");

        Output output = new Output();
        output.setId(updateDTO.getId());
        output.setName(updateDTO.getName());

        when(outputService.update(any(OutputUpdateDTO.class))).thenReturn(output);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/form/output")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updateDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Output"));
    }

    @Test
    public void testDeleteOutput() throws Exception {
        // Arrange
        OutputDeleteDTO deleteDTO = new OutputDeleteDTO();
        deleteDTO.setId(UUID.randomUUID());
        deleteDTO.setName("Delete Output");

        Output output = new Output();
        output.setId(deleteDTO.getId());
        output.setName(deleteDTO.getName());

        when(outputService.delete(any(OutputDeleteDTO.class))).thenReturn(output);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/form/output")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deleteDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Delete Output"));
    }
}
