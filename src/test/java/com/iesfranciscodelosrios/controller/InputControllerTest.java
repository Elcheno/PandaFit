package com.iesfranciscodelosrios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.input.InputResponseDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.service.InputService;
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
public class InputControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InputService inputService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetInputById() throws Exception {
        // Arrange
        UUID inputId = UUID.randomUUID();
        Input input = new Input();
        input.setId(inputId);
        input.setName("Test Input");

        when(inputService.findById(inputId)).thenReturn(input);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/form/input/{id}", inputId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(inputId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Input"));
    }

    @Test
    public void testGetAllInputs() throws Exception {
        // Arrange
        UUID inputId = UUID.randomUUID();
        Input input = new Input();
        input.setId(inputId);
        input.setName("Test Input");

        Page<Input> inputPage = new PageImpl<>(Collections.singletonList(input));

        when(inputService.findAll(any(Pageable.class))).thenReturn(inputPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/form/inputs/page")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(inputId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Test Input"));
    }

    @Test
    public void testCreateInput() throws Exception {
        // Arrange
        InputCreateDTO createDTO = new InputCreateDTO();
        createDTO.setName("New Input");

        Input input = new Input();
        input.setId(UUID.randomUUID());
        input.setName("New Input");

        when(inputService.save(any(InputCreateDTO.class))).thenReturn(input);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/form/input")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Input"));
    }

    @Test
    public void testUpdateInput() throws Exception {
        // Arrange
        InputUpdateDTO updateDTO = new InputUpdateDTO();
        updateDTO.setId(UUID.randomUUID());
        updateDTO.setName("Updated Input");

        Input input = new Input();
        input.setId(updateDTO.getId());
        input.setName(updateDTO.getName());

        when(inputService.update(any(InputUpdateDTO.class))).thenReturn(input);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/form/input")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updateDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Input"));
    }

    @Test
    public void testDeleteInput() throws Exception {
        // Arrange
        InputDeleteDTO deleteDTO = new InputDeleteDTO();
        deleteDTO.setId(UUID.randomUUID());
    //    deleteDTO.setName("Delete Input");

        Input input = new Input();
        input.setId(deleteDTO.getId());
    //    input.setName(deleteDTO.getName());

        when(inputService.delete(any(InputDeleteDTO.class))).thenReturn(input);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/form/input")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deleteDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Delete Input"));
    }
}

