package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.answer.*;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/active/response")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * Retrieve an answer by its date.
     *
     * @param answerDate The date of the answer to retrieve.
     * @return ResponseEntity containing the retrieved answer or a not found status.
     */
    @GetMapping("/byDate")
    public ResponseEntity<AnswerResponseDTO> getAnswerByDate(@RequestParam("date") LocalDateTime answerDate) {
            Answer answerEntity = answerService.loadAnswerByDate(answerDate);

            if (answerEntity == null) return ResponseEntity.notFound().build();

            AnswerResponseDTO answerResponseDTO = answerService.mapToResponseDTO(answerEntity);
            return ResponseEntity.ok(answerResponseDTO);
    }

    /**
     *
     * @param query
     * @return
     */
    @PostMapping("/query")
    public ResponseEntity<List<AnswerPrettyResponseDTO>> customQuery(@RequestBody List<AnswerQueryDTO> query) {
        List<Answer> result = this.answerService.handlerCustomQuery(query);

        if (result == null) return ResponseEntity.ok(Collections.emptyList());

        List<AnswerPrettyResponseDTO> response = this.answerService.mapToQueryResponseDTO(result);
        return ResponseEntity.ok(response);
    }

    /**
     * Find by name
     * @param uuid name substring of the Answer's uuid
     * @return the list of AnswerResponseDTOs or 404 if none found
     */
    @GetMapping("schoolyear/{id}/uuid")
    public ResponseEntity<Page<AnswerPrettyResponseDTO>> findByName(@PageableDefault() Pageable pageable,
                                                              @PathVariable("id") String id,
                                                              @RequestParam("uuid") String uuid) {
        Page<Answer> result = answerService.findAllByUuid(pageable, UUID.fromString(id), uuid);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerPrettyResponseDTO> response = this.answerService.mapToPrettyResponseDTO(result);

        return ResponseEntity.ok(response);
    }

    /**
     * Find by form name
     * @param formName name substring of the Answer's uuid
     * @return the list of AnswerResponseDTOs or 404 if none found
     */
    @GetMapping("schoolyear/{id}/name")
    public ResponseEntity<Page<AnswerPrettyResponseDTO>> findByFormName(@PageableDefault() Pageable pageable,
                                                                    @PathVariable("id") String id,
                                                                    @RequestParam("name") String formName) {
        Page<Answer> result = answerService.findAllByFormName(pageable, UUID.fromString(id), formName);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerPrettyResponseDTO> response = this.answerService.mapToPrettyResponseDTO(result);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all answers relative to a given date based on a before, after, or equal condition.
     *
     * @param pageable Pagination information.
     * @param id The school year ID.
     * @param answerDate The date in question.
     * @param beforeOrAfter Condition to filter by (before, after, equal).
     * @return ResponseEntity containing the page of AnswerResponseDTOs or a not found status.
     */
    @GetMapping("/schoolyear/{id}/date")
    public ResponseEntity<Page<AnswerPrettyResponseDTO>> getAllAnswersByDate(
            @PageableDefault() Pageable pageable,
            @PathVariable("id") String id,
            @RequestParam("date") LocalDateTime answerDate,
            @RequestParam("BeforeOrAfter") String beforeOrAfter) throws Exception {

        Page<Answer> answerEntity;
        UUID schoolYearId = UUID.fromString(id);

        switch (beforeOrAfter.toLowerCase()) {
            case "before":

                LocalDateTime past = answerDate.minusYears(100);
                answerEntity = answerService.findAllByDateBetween(past, answerDate, schoolYearId, pageable);
                break;
            case "after":

                LocalDateTime now = LocalDateTime.now();
                answerEntity = answerService.findAllByDateBetween(answerDate, now, schoolYearId, pageable);
                break;
            default:

                LocalDateTime ahora = LocalDateTime.now();
                answerEntity = answerService.findAllByDateBetween(answerDate, ahora, schoolYearId, pageable);
        }

        if (answerEntity == null) {
            return ResponseEntity.notFound().build();
        }

        Page<AnswerPrettyResponseDTO> response = this.answerService.mapToPrettyResponseDTO(answerEntity);
        return ResponseEntity.ok(response);
    }


    /**
     * Retrieve an answer by its ID.
     *
     * @param id The ID of the answer to retrieve.
     * @return ResponseEntity containing the retrieved answer or a not found status.
     */
    @GetMapping("{id}")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@PathVariable("id") UUID id) {
        Answer answerEntity = answerService.findById(id);

        if (answerEntity == null) return ResponseEntity.notFound().build();

        AnswerResponseDTO answerResponseDTO = answerService.mapToResponseDTO(answerEntity);
        return ResponseEntity.ok(answerResponseDTO);
    }

    /**
     * Retrieve all answers with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of answers or a bad request status.
     */
    @GetMapping("page")
    public ResponseEntity<Page<AnswerResponseDTO>> getAllAnswers(@PageableDefault() Pageable pageable) {
        Page<Answer> result = answerService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerResponseDTO> response = result.map(answerService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all answers with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of answers or a bad request status.
     */
    @GetMapping("page/formAct/{id}")
    public ResponseEntity<Page<AnswerResponseDTO>> getAllAnswersByFormAct(@PageableDefault() Pageable pageable, @PathVariable("id") String id) {
        Page<Answer> result = answerService.findAllByFormAct(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerResponseDTO> response = result.map(answerService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all answers with pagination.
     *
     * @param pageable Pagination information.
     * @return ResponseEntity containing a page of answers or a bad request status.
     */
    @GetMapping("page/form/{id}")
    public ResponseEntity<Page<AnswerResponseDTO>> getAllAnswersByForm(@PageableDefault() Pageable pageable, @PathVariable("id") String id) {
        Page<Answer> result = answerService.findAllByForm(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerResponseDTO> response = result.map(answerService::mapToResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("page/schoolyear/{id}")
    public ResponseEntity<Page<AnswerPrettyResponseDTO>> getAllAnswersBySchoolYear(@PageableDefault() Pageable pageable, @PathVariable("id") String id) {
        Page<Answer> result = answerService.findAllBySchoolYear(UUID.fromString(id), pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerPrettyResponseDTO> response = this.answerService.mapToPrettyResponseDTO(result);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new answer.
     *
     * @param answerCreateDTO DTO containing information for creating the answer.
     * @return ResponseEntity containing the created answer or a bad request status.
     */
    @PostMapping()
    public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody AnswerCreateDTO answerCreateDTO) {
        Answer answerEntity = answerService.save(answerCreateDTO);

        if (answerEntity == null) return ResponseEntity.badRequest().build();

        AnswerResponseDTO answerResponseDTO = answerService.mapToResponseDTO(answerEntity);
        return new ResponseEntity<>(answerResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Delete an answer.
     *
     * @param answerDeleteDTO DTO containing information for deleting the answer.
     * @return ResponseEntity containing a success message or a not found status.
     */
    @DeleteMapping()
    public ResponseEntity<String> deleteAnswer( @RequestBody AnswerDeleteDTO answerDeleteDTO) {
        boolean deleted = answerService.delete(answerDeleteDTO);

        if (deleted) {
            return ResponseEntity.ok("Respuesta eliminada correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
