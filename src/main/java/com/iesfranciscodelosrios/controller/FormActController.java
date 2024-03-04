package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.formAct.*;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.service.FormActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.iesfranciscodelosrios.model.entity.Input;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/active")
public class FormActController {

    @Autowired
    private FormActService formActService;

    /**
     * Retrieve a form action by its ID.
     *
     * @param id The ID of the form action to retrieve.
     * @return ResponseEntity containing the retrieved form action or a not found status.
     */
    @GetMapping("{id}")
    public ResponseEntity<FormActResponseDTO> getFormActById(@PathVariable("id") String id) {
        FormAct formAct = formActService.findById(UUID.fromString(id));
        if (formAct == null) return ResponseEntity.notFound().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    /**
     * Retrieve all form actions with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of form actions or a bad request status.
     */
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

    /**
     * Retrieves all form actions for a given school year with pagination.
     *
     * @param pageable Pagination information.
     * @param id       The ID of the school year.
     * @return ResponseEntity containing a page of form actions for the specified school year or a bad request status.
     */
    @GetMapping("page/schoolYear/{id}")
    public ResponseEntity<Page<FormActResponseDTO>> getAllFormsActBySchoolYear(
            @PageableDefault() Pageable pageable,
            @PathVariable("id") String id) {
        Page<FormAct> result = formActService.findAllBySchoolYear(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormActResponseDTO> response = result.map(formAct -> {
            return FormActResponseDTO.builder()
                    .id(formAct.getId())
                    .startDate(formAct.getStartDate())
                    .expirationDate(formAct.getExpirationDate())
                    .formId(formAct.getForm().getId().toString())
                    .formName(formAct.getForm().getName())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all form actions for a given school year after a specified expiration date with pagination.
     *
     * @param pageable Pagination information.
     * @param id       The ID of the school year.
     * @return ResponseEntity containing a page of form actions for the specified school year and after the expiration date or a bad request status.
     */
    @GetMapping("page/schoolYear/after/{id}")
    public ResponseEntity<Page<FormActResponseDTO>> getAllFormsActBySchoolYearAfterExpirationDate(
            @PageableDefault() Pageable pageable,
            @PathVariable("id") String id) {
        Page<FormAct> result = formActService.findAllBySchoolYearAfterExpirationDate(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormActResponseDTO> response = result.map(formAct -> {
            return FormActResponseDTO.builder()
                    .id(formAct.getId())
                    .startDate(formAct.getStartDate())
                    .expirationDate(formAct.getExpirationDate())
                    .formId(formAct.getForm().getId().toString())
                    .formName(formAct.getForm().getName())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all form actions for a given school year before a specified expiration date with pagination.
     *
     * @param pageable Pagination information.
     * @param id       The ID of the school year.
     * @return ResponseEntity containing a page of form actions for the specified school year and before the expiration date or a bad request status.
     */
    @GetMapping("page/schoolYear/before/{id}")
    public ResponseEntity<Page<FormActResponseDTO>> getAllFormsActBySchoolYearBeforeExpirationDate(
            @PageableDefault() Pageable pageable,
            @PathVariable("id") String id) {
        Page<FormAct> result = formActService.findAllBySchoolYearBeforeExpirationDate(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<FormActResponseDTO> response = result.map(formAct -> {
            return FormActResponseDTO.builder()
                    .id(formAct.getId())
                    .startDate(formAct.getStartDate())
                    .expirationDate(formAct.getExpirationDate())
                    .formId(formAct.getForm().getId().toString())
                    .formName(formAct.getForm().getName())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new form action.
     *
     * @param formActCreateDTO DTO containing information for creating the form action.
     * @return ResponseEntity containing the created form action or a bad request status.
     */
    @PostMapping()
    public ResponseEntity<FormActResponseDTO> createFormAct(@RequestBody FormActCreateDTO formActCreateDTO) {
        FormAct formAct = formActService.save(formActCreateDTO);
        if (formAct == null) return ResponseEntity.badRequest().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    /**
     * Closes a form action.
     *
     * @param formActCloseDTO DTO containing information for closing the form action.
     * @return ResponseEntity containing the closed form action or a bad request status.
     */
    @PutMapping("/close")
    public ResponseEntity<FormActResponseDTO> createFormAct(@RequestBody FormActCloseDTO formActCloseDTO) {
        FormAct formAct = formActService.closeForm(formActCloseDTO);
        if (formAct == null) return ResponseEntity.badRequest().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    /**
     * Deletes a form action by its ID.
     *
     * @param formActDeleteDTO DTO containing the ID of the form action to delete.
     * @return ResponseEntity containing the deleted form action or a not found status.
     */
    @DeleteMapping()
    public ResponseEntity<FormActResponseDTO> deleteFormActById(@RequestBody FormActDeleteDTO formActDeleteDTO) {
        FormAct formAct = formActService.delete(formActDeleteDTO);

        if (formAct == null) return ResponseEntity.notFound().build();

        FormActResponseDTO formActResponseDTO = formActService.mapToResponseDTO(formAct);
        return ResponseEntity.ok(formActResponseDTO);
    }

    /**
     * Retrieves the details of a form, formAct and inputs by its formAct ID.
     *
     * @param id The ID of the form action.
     * @return ResponseEntity containing the form details if found, otherwise returns a 404 Not Found response.
     */
    @GetMapping("{id}/form")
    public ResponseEntity<Map<String, Object>> getFormDetailsByFormActId(@PathVariable("id") String id) {
        FormAct formAct = formActService.findById(UUID.fromString(id));

        if (formAct == null) {
            return ResponseEntity.notFound().build();
        }

        Form form = formAct.getForm();

        // Esto es lo que hay que comentar de la comparación de la fecha de expiración con la actual
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = formAct.getExpirationDate().toLocalDate();
        if (expirationDate != null && expirationDate.isBefore(currentDate)) {
            // La fecha de expiración es anterior a la fecha actual, lanzar un error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El formulario ha expirado"));
        }


        //Hasta aquí

        Map<String, Object> responseData = new HashMap<>();

        List<Map<String, Object>> inputDetails = new ArrayList<>();
        for (Input input : form.getInputList()) {
            Map<String, Object> inputInfo = new HashMap<>();
            inputInfo.put("id", input.getId());
            inputInfo.put("name", input.getName());
            inputInfo.put("description", input.getDescription());
            inputInfo.put("type", input.getType());
            inputInfo.put("decimal", input.getDecimal());
            inputInfo.put("decimals", input.getDecimals());
            inputInfo.put("unit", input.getUnit());
            inputDetails.add(inputInfo);
        }
        responseData.put("inputs", inputDetails);

        responseData.put("startDate", formAct.getStartDate());
        responseData.put("expirationDate", formAct.getExpirationDate());

        Map<String, Object> formDetails = new HashMap<>();
        formDetails.put("id", form.getId());
        formDetails.put("name", form.getName());
        formDetails.put("description", form.getDescription());
        responseData.put("formDetails", formDetails);

        return ResponseEntity.ok(responseData);
    }
}
