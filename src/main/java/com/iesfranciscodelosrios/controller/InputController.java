package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.input.InputResponseDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
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
    @GetMapping("/input/name")
    public ResponseEntity<InputResponseDTO> findByName(@RequestParam("name") String name) {
        Input input = InputService.findByName(name);

        if (input != null) return ResponseEntity.notFound().build();

        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    /**
     * Retrieves all inputs containing the given name with pagination.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @return ResponseEntity containing a page of inputs or a bad request status.
     */
    @GetMapping("/page/name")
    public ResponseEntity<Page<InputResponseDTO>> getAllInputsByNameContaining(@PageableDefault(sort = "name") Pageable pageable, @RequestParam("name") String name) {
        Page<Input> result = InputService.findAllByNameContaining(pageable, name);
        if (result == null) return ResponseEntity.badRequest().build();

        Page<InputResponseDTO> response = result.map(input -> InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
                .build());

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves an Input by its ID.
     *
     * @param id The ID of the input to retrieve.
     * @return ResponseEntity containing an InputResponseDTO or not found if the input does not exist.
     */
    @GetMapping("/input/{id}")
    public ResponseEntity<InputResponseDTO> findById(@PathVariable("id") UUID id) {
        Input input = InputService.findById(id);

        if (input == null) return ResponseEntity.notFound().build();

        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    /**
     * Retrieves all inputs with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of inputs or a bad request status.
     */
    @GetMapping("/input/page")
    public ResponseEntity<Page<InputResponseDTO>> getAllInputs(@PageableDefault() Pageable pageable) {
        Page<Input> result = InputService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<InputResponseDTO> response = result.map(input -> {
            return InputResponseDTO.builder()
                    .id(input.getId())
                    .name(input.getName())
                    .description(input.getDescription())
                    .type(input.getType().name())
                    .decimal(input.getDecimal())
                    .decimals(input.getDecimals())
                    .unit(input.getUnit())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a paginated list of inputs.
     *
     * @param pageable Pageable object for pagination information.
     * @return ResponseEntity containing a Page of InputResponseDTOs or no content if the page is empty.
     */
   /* @GetMapping("/inputs/page")
    public ResponseEntity<Page<InputResponseDTO>> getAllInputs(
            @PageableDefault() Pageable pageable) {

        Page<Input> result = InputService.findAll(pageable);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<InputResponseDTO> response = result.map(input -> InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
        //        .description(input.getDescription())
        //        .validator(input.getValidator())
                .build()
        );

        return ResponseEntity.ok(response);
    }*/

    /**
     * Saves a new Input.
     *
     * @param inputCreateDTO InputCreateDTO containing information for creating a new Input.
     * @return ResponseEntity containing the saved InputResponseDTO.
     */
    @PostMapping("/input")
    public ResponseEntity<InputResponseDTO> save(@RequestBody InputCreateDTO inputCreateDTO) {

        Input input = InputService.save(inputCreateDTO);

        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
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
        Input input = InputService.update(inputUpdateDTO);
        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }

    /**
     * Deletes an existing Input.
     *
     * @param  inputDeleteDTO containing information for deleting an existing Input.
     * @return ResponseEntity containing the deleted InputResponseDTO.
     */
    @DeleteMapping("/input")
    public ResponseEntity<InputResponseDTO> delete(@RequestBody InputDeleteDTO inputDeleteDTO) {
        Input input = InputService.delete(inputDeleteDTO);
        InputResponseDTO inputResponseDTO = InputResponseDTO.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .type(input.getType().name())
                .decimal(input.getDecimal())
                .decimals(input.getDecimals())
                .unit(input.getUnit())
                .build();
        return ResponseEntity.ok(inputResponseDTO);
    }
}
