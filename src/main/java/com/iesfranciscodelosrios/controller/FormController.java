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

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<FormResponseDTO> getFormById(@PathVariable("id") UUID id){
        Form formEntity = formService.findById(id);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @GetMapping("page")
    public ResponseEntity<Page<FormResponseDTO>> getAllForms(@PageableDefault() Pageable pageable) {
        Page<Form> result = formService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormResponseDTO> response = result.map(form -> {
            return FormResponseDTO.builder()
                    .id(form.getId())
                    .name(form.getName())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<FormResponseDTO> createForm(@RequestBody FormCreateDTO formCreateDTO){
        Form formEntity = formService.save(Form.builder()
                .name(formCreateDTO.getName())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<FormResponseDTO> updateForm(@RequestBody FormUpdateDTO formUpdateDTO){
        Form formEntity = formService.save(Form.builder()
                .id(formUpdateDTO.getId())
                .name(formUpdateDTO.getName())
                .description(formUpdateDTO.getDescription())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<FormResponseDTO> deleteForm(@RequestBody() FormDeleteDTO formDeleteDTO){

        Form formEntity = formService.delete(Form.builder()
                .name(formDeleteDTO.getName())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }
}
