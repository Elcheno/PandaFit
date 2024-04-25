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

    /**
     * Retrieves a form by its name.
     *
     * @param name The name of the form to retrieve.
     * @return ResponseEntity containing the retrieved form or a not found status.
     */
    @GetMapping("name")
    public ResponseEntity<FormResponseDTO> getFormByName(@RequestParam("name") String name){
        Form formEntity = formService.loadFormByName(name);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    /**
     * Retrieves a form by its ID.
     *
     * @param id The ID of the form to retrieve.
     * @return ResponseEntity containing the retrieved form or a not found status.
     */
    @GetMapping("{id}")
    public ResponseEntity<FormResponseDTO> getFormById(@PathVariable("id") UUID id){
        Form formEntity = formService.findById(id);

        if (formEntity == null) return ResponseEntity.notFound().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    /**
     * Retrieves all forms with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of forms or a bad request status.
     */
    @GetMapping("page")
    public ResponseEntity<Page<FormResponseDTO>> getAllForms(@PageableDefault() Pageable pageable) {
        Page<Form> result = formService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormResponseDTO> response = result.map(formService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all forms containing the given name with pagination.
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @return ResponseEntity containing a page of forms or a bad request status.
     */
    @GetMapping("/page/name")
    public ResponseEntity<Page<FormResponseDTO>> getAllFormsByNameContaining(@PageableDefault() Pageable pageable, @RequestParam("name") String name) {
        Page<Form> result = formService.findAllByNameContaining(pageable, name);
        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormResponseDTO> response = result.map(form -> FormResponseDTO.builder()
                .id(form.getId())
                .name(form.getName())
                .build());

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new form.
     *
     * @param formCreateDTO DTO containing information for creating the form.
     * @return ResponseEntity containing the created form or a bad request status.
     */
    @PostMapping()
    public ResponseEntity<FormResponseDTO> createForm(@RequestBody FormCreateDTO formCreateDTO){
        Form formEntity = formService.save(formCreateDTO);

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    /**
     * Updates an existing form.
     *
     * @param formUpdateDTO DTO containing information for updating the form.
     * @return ResponseEntity containing the updated form or a bad request status.
     */
    @PutMapping()
    public ResponseEntity<FormResponseDTO> updateForm(@RequestBody FormUpdateDTO formUpdateDTO){
        Form formEntity = formService.update(formUpdateDTO);

        if (formEntity == null) return ResponseEntity.badRequest().build();

        FormResponseDTO formResponseDTO = formService.mapToResponseDTO(formEntity);
        return ResponseEntity.ok(formResponseDTO);
    }

    /**
     * Deletes a form by its ID.
     *
     * @param formDeleteDTO DTO containing the ID of the form to delete.
     * @return ResponseEntity containing a boolean indicating the success of the deletion or a not found status.
     */
    @DeleteMapping()
    public ResponseEntity<Boolean> deleteForm(@RequestBody FormDeleteDTO formDeleteDTO) {
        boolean deleted = formService.delete(formDeleteDTO);

        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a form if it's not in use.
     *
     * @param formDeleteDTO DTO containing the ID of the form to delete if not in use.
     * @return ResponseEntity containing a message indicating the success of the deletion or a not found status.
     */
    @DeleteMapping("/deleteIfNotUse")
    public ResponseEntity<String> deleteIfNotUseForm(@RequestBody FormDeleteDTO formDeleteDTO) {
        boolean deleted = formService.deleteIfNotUse(formDeleteDTO);

        if (deleted) {
            return ResponseEntity.ok("Formulario eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
