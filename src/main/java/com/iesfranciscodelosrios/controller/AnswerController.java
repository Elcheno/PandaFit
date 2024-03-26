package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerResponseDTO;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
