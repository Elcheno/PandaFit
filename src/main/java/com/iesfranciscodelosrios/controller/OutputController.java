package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputResponseDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/form")
public class OutputController {
    @Autowired
    private OutputService OutputService;

    /**
     * Find by name
     * @param name name of the output
     * @return the output or 404
     */
    @GetMapping("/output/name")
    public ResponseEntity<OutputResponseDTO> findByName(@RequestParam("name") String name) {
        Output output = OutputService.findByName(name);

        if (output != null) return ResponseEntity.notFound().build();

        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .umbralList(output.getUmbralList())
                .unit(output.getUnit())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    /**
     * Find by id
     * @param id id of the output
     * @return the output or 404
     */
    @GetMapping("/output/{id}")
    public ResponseEntity<OutputResponseDTO> findById(@PathVariable("id") UUID id) {
        Output output = OutputService.findById(id);

        if (output == null) return ResponseEntity.notFound().build();

        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .umbralList(output.getUmbralList())
                .unit(output.getUnit())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    /**
     * Retrieves a paginated list of outputs.
     *
     * @param pageable Pageable object for pagination information.
     * @return ResponseEntity containing a Page of OutputResponseDTOs or no content if the page is empty.
     */
    /*@GetMapping("/outputs/page")
    public ResponseEntity<Page<OutputResponseDTO>> getAllOutputs(
            @PageableDefault() Pageable pageable) {

        Page<Output> result = OutputService.findAll(pageable);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<OutputResponseDTO> response = result.map(output -> OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
        //        .description(output.getDescription())
        //        .formula(output.getFormula())
                .result(output.getResult())
                .build()
        );

        return ResponseEntity.ok(response);
    }*/

    @GetMapping("/output/page")
    public ResponseEntity<Page<OutputResponseDTO>> getAllOutputs(@PageableDefault() Pageable pageable) {
        Page<Output> result = OutputService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<OutputResponseDTO> response = result.map(output -> {
            return OutputResponseDTO.builder()
                    .id(output.getId())
                    .name(output.getName())
                    .description(output.getDescription())
                    .formula(output.getFormula())
                    .umbralList(output.getUmbralList())
                    .unit(output.getUnit())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    /**
     * Save an output
     * @param outputCreateDTO the output to save without id
     * @return the saved output with id
     */
    @PostMapping("/output")
    public ResponseEntity<OutputResponseDTO> save(@RequestBody OutputCreateDTO outputCreateDTO) {
        Output output = OutputService.save(outputCreateDTO);
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .umbralList(output.getUmbralList())
                .inputsId(output.getInputs().stream().map((item) -> item.getId().toString()).toList())
                .unit(output.getUnit())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    /**
     * Update an output
     * @param outputUpdateDTO the output to update
     * @return the updated output
     */
    @PutMapping("/output")
    public ResponseEntity<OutputResponseDTO> update(@RequestBody OutputUpdateDTO outputUpdateDTO) {
        Output output = OutputService.update(outputUpdateDTO);
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .umbralList(output.getUmbralList())
                .unit(output.getUnit())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    /**
     * Delete an output
     * @param outputDeleteDTO the output to delete
     * @return the deleted output
     */
    @DeleteMapping("/output")
    public ResponseEntity<OutputResponseDTO> delete(@RequestBody OutputDeleteDTO outputDeleteDTO) {
        Output output = OutputService.delete(outputDeleteDTO);
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .umbralList(output.getUmbralList())
                .unit(output.getUnit())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }
}
