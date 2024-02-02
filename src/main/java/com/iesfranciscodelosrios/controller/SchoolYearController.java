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

    /**
     * Retrieves a SchoolYear by its ID.
     *
     * @param id The ID of the school year to retrieve.
     * @return ResponseEntity containing a SchoolYearResponseDTO or not found if the school year does not exist.
     */
    @GetMapping("/institution/schoolYear/{id}")
    public ResponseEntity<SchoolYearResponseDTO> getSchoolYearById(@PathVariable("id") String id) {
        SchoolYear schoolYear = schoolYearService.findById(UUID.fromString(id));

        if (schoolYear == null) return ResponseEntity.notFound().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }


    /**
     * Retrieves a SchoolYear by its name and institution ID.
     *
     * @param institutionId The ID of the institution to which the school year belongs.
     * @param name The name of the school year to retrieve.
     * @return ResponseEntity containing a SchoolYearResponseDTO or not found if the school year does not exist.
     */
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
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    /**
     * Retrieves a paginated list of school years for a specific institution.
     *
     * @param institutionId The ID of the institution to which the school years belong.
     * @param pageable Pageable object for pagination information.
     * @return ResponseEntity containing a Page of SchoolYearResponseDTOs or no content if the page is empty.
     */
    @GetMapping("/institution/{institutionId}/schoolYear/page")
    public ResponseEntity<Page<SchoolYearResponseDTO>> getAllSchoolYears(
            @PathVariable("institutionId") String institutionId,
            @PageableDefault() Pageable pageable) {


        Page<SchoolYear> result = schoolYearService.findAllByInstitution(
                institutionService.findById(UUID.fromString(institutionId)),
                pageable
        );

        if (result == null) return ResponseEntity.badRequest().build();

        Page<SchoolYearResponseDTO> response = result.map(schoolYear -> SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .build()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new SchoolYear.
     *
     * @param schoolYearCreateDTO SchoolYearCreateDTO containing information for creating a new SchoolYear.
     * @return ResponseEntity containing the created SchoolYearResponseDTO.
     */
    @PostMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> createSchoolYear(@RequestBody SchoolYearCreateDTO schoolYearCreateDTO) {
        SchoolYear schoolYear = schoolYearService.save(SchoolYear.builder()
                        .name(schoolYearCreateDTO.getName())
                        .institution(institutionService.findById(schoolYearCreateDTO.getInstitutionId()))
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    /**
     * Deletes an existing SchoolYear.
     *
     * @param schoolYearResponseDTO SchoolYearResponseDTO containing information for deleting an existing SchoolYear.
     * @return ResponseEntity containing the deleted SchoolYearResponseDTO.
     */
    @DeleteMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> deleteSchoolYear(@RequestBody SchoolYearResponseDTO schoolYearResponseDTO) {
        SchoolYear schoolYear = schoolYearService.delete(SchoolYear.builder()
                        .id(UUID.fromString(String.valueOf(schoolYearResponseDTO.getId())))
                        .name(schoolYearResponseDTO.getName())
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(schoolYearResponseDTO);
    }

    /**
     * Updates an existing SchoolYear.
     *
     * @param schoolYearUpdateDTO SchoolYearUpdateDTO containing information for updating an existing SchoolYear.
     * @return ResponseEntity containing the updated SchoolYearResponseDTO.
     */
    @PutMapping("/institution/schoolYear")
    public ResponseEntity<SchoolYearResponseDTO> updateSchoolYear(@RequestBody SchoolYearUpdateDTO schoolYearUpdateDTO) {
        SchoolYear schoolYear = schoolYearService.save(SchoolYear.builder()
                        .id(schoolYearUpdateDTO.getId())
                        .name(schoolYearUpdateDTO.getName())
                        .build());

        if (schoolYear == null) return ResponseEntity.badRequest().build();

        SchoolYearResponseDTO schoolYearResponseDTO = SchoolYearResponseDTO.builder()
                .id(schoolYear.getId())
                .name(schoolYear.getName())
                .build();

        return ResponseEntity.ok(schoolYearResponseDTO);

    }
}
