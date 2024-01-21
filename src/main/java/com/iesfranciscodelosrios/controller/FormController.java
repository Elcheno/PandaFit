package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/form/formulary")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping("name")
    public ResponseEntity<FormResponseDTO> getFormByName(@PathVariable("name") String name){
        Form formEntity = formService.loadFormByName(name);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .userOwner(formEntity.getUserOwner())
                .formActList(formEntity.getFormActList())
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
                .userOwner(formEntity.getUserOwner())
                .formActList(formEntity.getFormActList())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<FormResponseDTO> createForm(@RequestBody FormCreateDTO formCreateDTO){
        Form formEntity = formService.save(Form.builder()
                .name(formCreateDTO.getName())
                .description(formCreateDTO.getDescription())
                .userOwner(formCreateDTO.getUserOwner())
                .formActList(formCreateDTO.getFormActList())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .userOwner(formEntity.getUserOwner())
                .formActList(formEntity.getFormActList())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<FormResponseDTO> updateForm(@RequestBody FormUpdateDTO formUpdateDTO){
        Form formEntity = formService.save(Form.builder()
                .id(formUpdateDTO.getId())
                .name(formUpdateDTO.getName())
                .description(formUpdateDTO.getDescription())
                .userOwner(formUpdateDTO.getUserOwner())
                .formActList(formUpdateDTO.getFormActList())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .userOwner(formEntity.getUserOwner())
                .formActList(formEntity.getFormActList())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<FormResponseDTO> deleteForm(@RequestBody() FormDeleteDTO formDeleteDTO){

        Form formEntity = formService.delete(Form.builder()
                .name(formDeleteDTO.getName())
                .description(formDeleteDTO.getDescription())
                .userOwner(formDeleteDTO.getUserOwner())
                .formActList(formDeleteDTO.getFormActList())
                .build());

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = FormResponseDTO.builder()
                .id(formEntity.getId())
                .name(formEntity.getName())
                .description(formEntity.getDescription())
                .userOwner(formEntity.getUserOwner())
                .formActList(formEntity.getFormActList())
                .build();

        return ResponseEntity.ok(formResponseDTO);
    }
}
