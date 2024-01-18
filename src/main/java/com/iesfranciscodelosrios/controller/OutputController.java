package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputResponseDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/form")
public class OutputController {
    @Autowired
    private OutputService OutputService;

    @GetMapping("/output/name/:name")
    public ResponseEntity<OutputResponseDTO> findByName(@RequestParam("name") String name) {
        Output output = OutputService.findByName(name);

        if (output != null) return ResponseEntity.notFound().build();

        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .userOwner(output.getUserOwner())
                .result(output.getResult())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    @GetMapping("/output/:id")
    public ResponseEntity<OutputResponseDTO> findById(@RequestParam("id") UUID id) {
        Output output = OutputService.findById(id);

        if (output == null) return ResponseEntity.notFound().build();

        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .userOwner(output.getUserOwner())
                .result(output.getResult())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    @PostMapping("/output")
    public ResponseEntity<OutputResponseDTO> save(@RequestBody OutputCreateDTO outputCreateDTO) {
        Output output = OutputService.save(Output.builder()
                .name(outputCreateDTO.getName())
                .description(outputCreateDTO.getDescription())
                .formula(outputCreateDTO.getFormula())
                .userOwner(outputCreateDTO.getUserOwner())
                .build());
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .userOwner(output.getUserOwner())
                .result(output.getResult())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    @PutMapping("/output")
    public ResponseEntity<OutputResponseDTO> update(@RequestBody OutputUpdateDTO outputUpdateDTO) {
        Output output = OutputService.save(Output.builder()
                .id(outputUpdateDTO.getId())
                .name(outputUpdateDTO.getName())
                .description(outputUpdateDTO.getDescription())
                .formula(outputUpdateDTO.getFormula())
                .userOwner(outputUpdateDTO.getUserOwner())
                .build());
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .userOwner(output.getUserOwner())
                .result(output.getResult())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }

    @DeleteMapping("/output")
    public ResponseEntity<OutputResponseDTO> delete(@RequestBody OutputUpdateDTO outputUpdateDTO) {
        Output output = OutputService.save(Output.builder()
                .id(outputUpdateDTO.getId())
                .name(outputUpdateDTO.getName())
                .description(outputUpdateDTO.getDescription())
                .formula(outputUpdateDTO.getFormula())
                .userOwner(outputUpdateDTO.getUserOwner())
                .build());
        OutputResponseDTO outputResponseDTO = OutputResponseDTO.builder()
                .id(output.getId())
                .name(output.getName())
                .description(output.getDescription())
                .formula(output.getFormula())
                .userOwner(output.getUserOwner())
                .result(output.getResult())
                .build();
        return ResponseEntity.ok(outputResponseDTO);
    }
}
