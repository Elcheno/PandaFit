package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputResponseDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/form")
public class InputController {
    @Autowired
    private InputService InputService;

    @GetMapping("/input/nam/:name")
    public ResponseEntity<InputResponseDTO> findByName(@RequestParam("name") String name) {
        Input input = InputService.findByName(name);

        if (input != null) return ResponseEntity.notFound().build();

        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .userOwner(input.getUserOwner())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    @GetMapping("/input/:id")
    public ResponseEntity<InputResponseDTO> findById(@RequestParam("id") UUID id) {
        Input input = InputService.findById(id);

        if (input == null) return ResponseEntity.notFound().build();

        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .userOwner(input.getUserOwner())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    @PostMapping("/input")
    public ResponseEntity<InputResponseDTO> save(@RequestBody InputCreateDTO inputCreateDTO) {
        Input input = InputService.save(Input.builder()
                .name(inputCreateDTO.getName())
                .description(inputCreateDTO.getDescription())
                .validator(inputCreateDTO.getValidator())
                .userOwner(inputCreateDTO.getUserOwner())
                .build());
        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .userOwner(input.getUserOwner())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    @PutMapping("/input")
    public ResponseEntity<InputResponseDTO> update(@RequestBody InputUpdateDTO inputUpdateDTO) {
        Input input = InputService.save(Input.builder()
                .id(inputUpdateDTO.getId())
                .name(inputUpdateDTO.getName())
                .description(inputUpdateDTO.getDescription())
                .validator(inputUpdateDTO.getValidator())
                .userOwner(inputUpdateDTO.getUserOwner())
                .build());
        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .userOwner(input.getUserOwner())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    @DeleteMapping("/input")
    public ResponseEntity<InputResponseDTO> delete(@RequestBody InputResponseDTO inputResponseDTO) {
        Input input = InputService.delete(Input.builder()
                .id(inputResponseDTO.getId())
                .name(inputResponseDTO.getName())
                .description(inputResponseDTO.getDescription())
                .validator(inputResponseDTO.getValidator())
                .userOwner(inputResponseDTO.getUserOwner())
                .build());
        InputResponseDTO response = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .userOwner(input.getUserOwner())
                .build();
        return ResponseEntity.ok(response);
    }
}
