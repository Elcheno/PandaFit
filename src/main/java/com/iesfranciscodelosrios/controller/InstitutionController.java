package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.institution.*;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    /**
     * Retrieves an institution by its ID.
     *
     * @param id The ID of the institution to retrieve.
     * @return ResponseEntity containing the retrieved institution or a not found status.
     */
    @GetMapping("{id}")
    public ResponseEntity<InstitutionResponseCompleteDTO> getInstitutionById(@PathVariable("id") String id) {
        Institution institution = institutionService.findById(UUID.fromString(id));

        if (institution == null) return ResponseEntity.notFound().build();

        InstitutionResponseCompleteDTO institutionResponseDTO = InstitutionResponseCompleteDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .schoolYearQuantity(institution.getSchoolYearList().size())
                .userQuantity(institution.getUserList().size())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    /**
     * Retrieves all institutions with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of institutions or a bad request status.
     */
    @GetMapping("page")
    public ResponseEntity<Page<InstitutionResponseDTO>> getAllInstitutions(@PageableDefault() Pageable pageable) {
        Page<Institution> result = institutionService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<InstitutionResponseDTO> response = result.map(institution -> InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .build());

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all institutions containing the given name with pagination.
     *
     * @param pageable Pagination information.
     * @param name     The name to search for.
     * @return ResponseEntity containing a page of institutions or a bad request status.
     */
    @GetMapping("/page/name")
    public ResponseEntity<Page<InstitutionResponseDTO>> getAllInstitutionsByNameContaining(@PageableDefault(sort = "name") Pageable pageable, @RequestParam("name") String name) {
        Page<Institution> result = institutionService.findAllByNameContaining(pageable, name);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<InstitutionResponseDTO> response = result.map(institution -> InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .build());

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new institution.
     *
     * @param institutionCreateDTO DTO containing information for creating the institution.
     * @return ResponseEntity containing the created institution or a bad request status.
     */
    @PostMapping()
    public ResponseEntity<InstitutionResponseDTO> createInstitution(@RequestBody InstitutionCreateDTO institutionCreateDTO) {
        Institution institution = institutionService.save(institutionCreateDTO);

        if (institution == null) return ResponseEntity.badRequest().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    /**
     * Updates an existing institution.
     *
     * @param institutionUpdateDTO DTO containing information for updating the institution.
     * @return ResponseEntity containing the updated institution or a bad request status.
     */
    @PutMapping()
    public ResponseEntity<InstitutionResponseDTO> updateInstitution(@RequestBody InstitutionUpdateDTO institutionUpdateDTO) {
        Institution institution = institutionService.update(institutionUpdateDTO);

        if (institution == null) return ResponseEntity.badRequest().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

    /**
     * Deletes an institution.
     *
     * @param institutionDeleteDTO DTO containing the ID of the institution to delete.
     * @return ResponseEntity containing the deleted institution or a bad request status.
     */
    @DeleteMapping()
    public ResponseEntity<InstitutionResponseDTO> deleteInstitution(@RequestBody InstitutionDeleteDTO institutionDeleteDTO) {
        Institution institution = institutionService.delete(institutionDeleteDTO);

        if (institution == null) return ResponseEntity.badRequest().build();

        InstitutionResponseDTO institutionResponseDTO = InstitutionResponseDTO.builder()
                .id(institution.getId())
                .name(institution.getName())
                .build();

        return ResponseEntity.ok(institutionResponseDTO);
    }

}
