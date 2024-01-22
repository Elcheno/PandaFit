package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearCreateDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearResponseDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearUpdateDTO;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.service.InstitutionService;
import com.iesfranciscodelosrios.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SchoolYearController {

    @Autowired
    private SchoolYearService schoolYearService;

    @Autowired
    private InstitutionService institutionService;

    @GetMapping("/institution/schoolYear/{id}")
    public ResponseEntity<SchoolYearResponseDTO> getSchoolYearById(@PathVariable("id") String id) {
        SchoolYear schoolYear = schoolYearService.findById(UUID.fromString(id));

        if (schoolYear == null) return ResponseEntity.notFound().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .institution(schoolYear.getInstitution())
                .formActList(schoolYear.getFormActList())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    //findById
    @GetMapping("/institution/{institutionId}/schoolYear/name")
    public ResponseEntity<SchoolYearResponseDTO> getSchoolYearByName(
            @PathVariable("institutionId") String institutionId,
            @RequestParam("name") String name) {

        SchoolYear schoolYear = schoolYearService.findByNameAndInstitution(
                name,
                institutionService.findById(UUID.fromString(institutionId)
        ));

        if (schoolYear == null) return ResponseEntity.notFound().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .institution(schoolYear.getInstitution())
                .formActList(schoolYear.getFormActList())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    @GetMapping("/institution/{institutionId}/schoolYear")
    public ResponseEntity<Page<SchoolYearResponseDTO>> getAllSchoolYears(
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {


        Page<SchoolYear> result = schoolYearService.findAllByInstitution(
                institutionService.findById(UUID.fromString(institutionId)),
                pageable
        );

        if (result == null) return ResponseEntity.badRequest().build();

        Page<SchoolYearResponseDTO> response = result.map(schoolYear -> {
            return SchoolYearResponseDTO.builder()
                    .id(schoolYear.getId())
                    .name(schoolYear.getName())
                    .institution(schoolYear.getInstitution())
                    .formActList(schoolYear.getFormActList())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    @PostMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> createSchoolYear(@RequestBody SchoolYearCreateDTO schoolYearCreateDTO) {
        SchoolYear schoolYear = schoolYearService.save(SchoolYear.builder()
                        .name(schoolYearCreateDTO.getName())
                        .institution(schoolYearCreateDTO.getInstitution())
                        .formActList(schoolYearCreateDTO.getFormActList())
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .institution(schoolYear.getInstitution())
                .formActList(schoolYear.getFormActList())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    @DeleteMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> deleteSchoolYear(@RequestBody SchoolYearResponseDTO schoolYearResponseDTO) {
        SchoolYear schoolYear = schoolYearService.delete(SchoolYear.builder()
                        .id(UUID.fromString(String.valueOf(schoolYearResponseDTO.getId())))
                        .name(schoolYearResponseDTO.getName())
                        .institution(schoolYearResponseDTO.getInstitution())
                        .formActList(schoolYearResponseDTO.getFormActList())
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    @PutMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> updateSchoolYear(@RequestBody SchoolYearUpdateDTO schoolYearUpdateDTO) {
        SchoolYear schoolYear = schoolYearService.save(SchoolYear.builder()
                        .id(schoolYearUpdateDTO.getId())
                        .name(schoolYearUpdateDTO.getName())
                        .institution(schoolYearUpdateDTO.getInstitution())
                        .formActList(schoolYearUpdateDTO.getFormActList())
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .institution(schoolYear.getInstitution())
                .formActList(schoolYear.getFormActList())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);

    }
}
