package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputResponseDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/form")
public class InputController {
    @Autowired
    private InputService InputService;

    /**
     * Retrieves an Input by its name.
     *
     * @param name The name of the input to retrieve.
     * @return ResponseEntity containing an InputResponseDTO or not found if the input does not exist.
     */
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

    /**
     * Retrieves an Input by its ID.
     *
     * @param id The ID of the input to retrieve.
     * @return ResponseEntity containing an InputResponseDTO or not found if the input does not exist.
     */
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

    /**
     * Retrieves a paginated list of inputs.
     *
     * @param pageable Pageable object for pagination information.
     * @return ResponseEntity containing a Page of InputResponseDTOs or no content if the page is empty.
     */
    @GetMapping("/inputs/page")
    public ResponseEntity<Page<InputResponseDTO>> getAllInputs(
            @PageableDefault() Pageable pageable) {

        Page<Input> result = InputService.findAll(pageable);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<InputResponseDTO> response = result.map(input -> InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .validator(input.getValidator())
                .build()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Saves a new Input.
     *
     * @param inputCreateDTO InputCreateDTO containing information for creating a new Input.
     * @return ResponseEntity containing the saved InputResponseDTO.
     */
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

    /**
     * Updates an existing Input.
     *
     * @param inputUpdateDTO InputUpdateDTO containing information for updating an existing Input.
     * @return ResponseEntity containing the updated InputResponseDTO.
     */
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

    /**
     * Deletes an existing Input.
     *
     * @param inputResponseDTO InputResponseDTO containing information for deleting an existing Input.
     * @return ResponseEntity containing the deleted InputResponseDTO.
     */
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
