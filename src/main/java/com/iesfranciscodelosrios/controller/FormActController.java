package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActDeleteDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActResponseDTO;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.service.FormActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/active/{id}")
public class FormActController {

    @Autowired
    private FormActService formActService;

    @PostMapping("/active/form/schoolYear/")
    public ResponseEntity<FormActResponseDTO> createFormAct(@RequestBody() FormActCreateDTO formActCreateDTO) {
        FormAct formAct = formActService.save(FormAct.builder()
                .startDate(formActCreateDTO.getStartDate())
                .expirationDate(formActCreateDTO.getExpirationDate())
                .form(formActCreateDTO.getForm())
                .schoolYear(formActCreateDTO.getSchoolYear())
                .answersList(formActCreateDTO.getAnswersList())
                .build());

        if (formAct == null) return ResponseEntity.badRequest().build();
        FormActResponseDTO formActResponseDTO = FormActResponseDTO.builder()
                .id(formAct.getId())
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .form(formAct.getForm())
                .schoolYear(formAct.getSchoolYear())
                .answersList(formAct.getAnswersList())
                .build();

        return ResponseEntity.ok(formActResponseDTO);
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<FormActResponseDTO> getFormActById(@PathVariable("id") String id) {
        FormAct formAct = formActService.findById(UUID.fromString(id));
        if (formAct == null) return ResponseEntity.notFound().build();
        FormActResponseDTO formActResponseDTO = FormActResponseDTO.builder()
                .id(formAct.getId())
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .form(formAct.getForm())
                .schoolYear(formAct.getSchoolYear())
                .answersList(formAct.getAnswersList())
                .build();
        return ResponseEntity.ok(formActResponseDTO);
    }

    @DeleteMapping("/active")
    public ResponseEntity<FormActResponseDTO> deleteFormActById(@RequestBody FormActDeleteDTO FormActDeleteDTO) {
        FormAct formAct = formActService.delete(FormAct.builder()
                .startDate(FormActDeleteDTO.getStartDate())
                .expirationDate(FormActDeleteDTO.getExpirationDate())
                .form(FormActDeleteDTO.getForm())
                .schoolYear(FormActDeleteDTO.getSchoolYear())
                .answersList(FormActDeleteDTO.getAnswersList())
                .build());

        if (formAct == null) return ResponseEntity.notFound().build();

        FormActResponseDTO formActResponseDTO = FormActResponseDTO.builder()
                .id(formAct.getId())
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .form(formAct.getForm())
                .schoolYear(formAct.getSchoolYear())
                .answersList(formAct.getAnswersList())
                .build();

        return ResponseEntity.ok(formActResponseDTO);
    }
}
