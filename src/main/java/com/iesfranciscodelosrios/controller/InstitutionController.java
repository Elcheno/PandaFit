package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionResponseDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping(":id")
    public ResponseEntity<InstitutionResponseDTO> getInstitutionById(@RequestParam("id") String id) {
        Institution institution = institutionService.findById(UUID.fromString(id));

        if (institution == null) return ResponseEntity.notFound().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .userList(institution.getUserList())
                .schoolYearList(institution.getSchoolYearList())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<InstitutionResponseDTO> createInstitution(@RequestBody InstitutionCreateDTO institutionCreateDTO) {
        Institution institution = institutionService.save(Institution.builder()
                        .name(institutionCreateDTO.getName())
                        .schoolYearList(institutionCreateDTO.getSchoolYearList())
                        .userList(institutionCreateDTO.getUserList())
                        .build());

        if (institution == null) return ResponseEntity.badRequest().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .userList(institution.getUserList())
                .schoolYearList(institution.getSchoolYearList())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    @DeleteMapping()
    public ResponseEntity<InstitutionResponseDTO> deleteInstitution(@RequestBody InstitutionResponseDTO institutionResponseDTO) {
        Institution institution = institutionService.delete(Institution.builder()
                        .id(UUID.fromString(String.valueOf(institutionResponseDTO.getId())))
                        .name(institutionResponseDTO.getName())
                        .userList(institutionResponseDTO.getUserList())
                        .schoolYearList(institutionResponseDTO.getSchoolYearList())
                        .build());

        if (institution == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<InstitutionResponseDTO> updateInstitution(@RequestBody InstitutionUpdateDTO institutionUpdateDTO) {
        Institution institution = institutionService.save(Institution.builder()
                        .id(institutionUpdateDTO.getId())
                        .name(institutionUpdateDTO.getName())
                        .userList(institutionUpdateDTO.getUserList())
                        .schoolYearList(institutionUpdateDTO.getSchoolYearList())
                        .build());

        if (institution == null) return ResponseEntity.badRequest().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .userList(institution.getUserList())
                .schoolYearList(institution.getSchoolYearList())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }


}
