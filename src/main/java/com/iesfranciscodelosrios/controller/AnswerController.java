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
@RequestMapping("/active/{idActive}/response")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/byDate/{date}")
    public ResponseEntity<AnswerResponseDTO> getAnswerByDate(@RequestParam("idActive") UUID formActId,
        @PathVariable("date") String answerDate) {

            System.out.println(answerDate);
            System.out.println(Timestamp.valueOf(LocalDateTime.parse(answerDate)));
            //Si ejecuto el test del servicio este funciona correctamente pero cuando llego al test del controlador
            //da null pointer exception al ejecutar el metodo del servicio y no se porque
            Answer answerEntity = answerService.loadAnswerByDate(LocalDateTime.parse(answerDate));

            if (answerEntity == null) return ResponseEntity.notFound().build();

            AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                    .id(answerEntity.getId())
                    .date(answerEntity.getDate())
                    .uuid(answerEntity.getUuid())
                    .build();

            return ResponseEntity.ok(answerResponseDTO);
    }


    @GetMapping("{id}")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@RequestParam("idActive") UUID formActId,
       @PathVariable("id") String answerId) {

        Answer answerEntity = answerService.findById(UUID.fromString(answerId));

        if (answerEntity == null) return ResponseEntity.notFound().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .uuid(answerEntity.getUuid())
                .build();

        return ResponseEntity.ok(answerResponseDTO);
    }

    @GetMapping("page")
    public ResponseEntity<Page<AnswerResponseDTO>> getAllAnswers(@PageableDefault() Pageable pageable) {
        Page<Answer> result = answerService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<AnswerResponseDTO> response = result.map(answer -> AnswerResponseDTO.builder()
                .id(answer.getId())
                .date(answer.getDate())
                .uuid(answer.getUuid())
                .build());

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<AnswerResponseDTO> createAnswer(@PathVariable("idActive") UUID formActId,
        @RequestBody AnswerCreateDTO answerCreateDTO) {

        Answer answerEntity = answerService.save(answerCreateDTO, formActId);
        if (answerEntity == null) return ResponseEntity.badRequest().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .uuid(answerEntity.getUuid())
                .build();

        return new ResponseEntity<>(answerResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<AnswerResponseDTO> deleteAnswer(@PathVariable("idActive") UUID formActId, @RequestBody AnswerDeleteDTO answerDeleteDTO) {
        Answer answerEntity = answerService.delete(answerDeleteDTO);

        if (answerEntity == null) return ResponseEntity.badRequest().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .uuid(answerEntity.getUuid())
                .build();

        return ResponseEntity.ok(answerResponseDTO);
    }
}
