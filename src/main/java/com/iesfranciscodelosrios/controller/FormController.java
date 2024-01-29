package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/form/formulary")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping("name")
    public ResponseEntity<FormResponseDTO> getFormByName(@RequestParam("name") String name){
        Form formEntity = formService.loadFormByName(name);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<FormResponseDTO> getFormById(@PathVariable("id") UUID id){
        Form formEntity = formService.findById(id);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    @GetMapping("page")
    public ResponseEntity<Page<FormResponseDTO>> getAllForms(@PageableDefault() Pageable pageable) {
        Page<Form> result = formService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormResponseDTO> response = result.map(formService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<FormResponseDTO> createForm(@RequestBody FormCreateDTO formCreateDTO){
        Form formEntity = formService.save(formCreateDTO);

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<FormResponseDTO> updateForm(@RequestBody FormUpdateDTO formUpdateDTO){
        Form formEntity = formService.update(formUpdateDTO);

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<FormResponseDTO> deleteForm(@RequestBody() FormDeleteDTO formDeleteDTO){
        Form formEntity = formService.delete(formDeleteDTO);

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }
}
