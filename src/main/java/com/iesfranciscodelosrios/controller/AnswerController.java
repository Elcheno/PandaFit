package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerResponseDTO;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/form/?/formActive/?")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/response")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@PathVariable("date") LocalDateTime answerDate) {
        Answer answerEntity = answerService.loadAnswerByDate(answerDate);

        if (answerEntity == null) return ResponseEntity.notFound().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .formAct(answerEntity.getFormAct())
                .uuid(answerEntity.getUuid())
                .build();

        return ResponseEntity.ok(answerResponseDTO);
    }


    //Rehacer bien hecho para tenerlo de ejemplo
    @GetMapping("/response")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@PathVariable("id") String answerId) {
        Answer answerEntity = answerService.findById(UUID.fromString(answerId));

        if (answerEntity == null) return ResponseEntity.notFound().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .formAct(answerEntity.getFormAct())
                .uuid(answerEntity.getUuid())
                .build();

        return ResponseEntity.ok(answerResponseDTO);
    }

    @PostMapping("/response")
    public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody AnswerCreateDTO answerCreateDTO) {

        Answer answerEntity = answerService.save(Answer.builder()
                .date(answerCreateDTO.getDate())
                .formAct(answerCreateDTO.getFormAct())
                .uuid(answerCreateDTO.getUuid())
                .build());

        if (answerEntity == null) return ResponseEntity.badRequest().build();

        AnswerResponseDTO answerResponseDTO = AnswerResponseDTO.builder()
                .id(answerEntity.getId())
                .date(answerEntity.getDate())
                .formAct(answerEntity.getFormAct())
                .uuid(answerEntity.getUuid())
                .build();

        return ResponseEntity.ok(answerResponseDTO);
    }

    @DeleteMapping("/response")
    public ResponseEntity<Void> deleteAnswer(@RequestBody AnswerDeleteDTO answerDeleteDTO) {

        if (answerDeleteDTO == null) return ResponseEntity.badRequest().build();

        Answer answerEntity = answerService.delete(Answer.builder()
                .date(answerDeleteDTO.getDate())
                .formAct(answerDeleteDTO.getFormAct())
                .uuid(answerDeleteDTO.getUuid())
                .build());

        if (answerEntity != null){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
