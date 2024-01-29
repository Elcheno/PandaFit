package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActDeleteDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.FormActService;
import com.iesfranciscodelosrios.service.FormService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/active")
public class FormActController {

    @Autowired
    private FormActService formActService;

    @GetMapping("{id}")
    public ResponseEntity<FormActResponseDTO> getFormActById(@PathVariable("id") String id) {
        FormAct formAct = formActService.findById(UUID.fromString(id));
        if (formAct == null) return ResponseEntity.notFound().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    @GetMapping("page")
    public ResponseEntity<Page<FormActResponseDTO>> getAllFormsAct(@PageableDefault() Pageable pageable) {
        Page<FormAct> result = formActService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormActResponseDTO> response = result.map(formAct -> {
            return FormActResponseDTO.builder()
                    .id(formAct.getId())
                    .startDate(formAct.getStartDate())
                    .expirationDate(formAct.getExpirationDate())
                    .build();
        });

        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<FormActResponseDTO> createFormAct(@RequestBody FormActCreateDTO formActCreateDTO) {
        FormAct formAct = formActService.save(formActCreateDTO);
        if (formAct == null) return ResponseEntity.badRequest().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<FormActResponseDTO> deleteFormActById(@RequestBody FormActDeleteDTO formActDeleteDTO) {
        FormAct formAct = formActService.delete(formActDeleteDTO);

        if (formAct == null) return ResponseEntity.notFound().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }
}
