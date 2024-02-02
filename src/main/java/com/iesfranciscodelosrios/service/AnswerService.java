package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService{
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    FormActService formActService;

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    /**
     * Loads an Answer based on the specified date.
     *
     * @param date The LocalDateTime representing the date to search for.
     * @return An Answer object if found, or null if not found.
     */
    public Answer loadAnswerByDate(LocalDateTime date) {
        try {
            Optional<Answer> answer = answerRepository.findAnswerByDate(date);
            return answer.orElse(null);
        } catch (Exception e) {
            logger.error("Error al cargar la respuesta por fecha: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all Answers with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing Answer objects based on the provided Pageable parameters.
     */
    public Page<Answer> findAll(Pageable pageable) {
        try {
            return answerRepository.findAll(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,

                            pageable.getSort()
                    )
            );
        } catch (Exception e) {
            logger.error("Error al buscar todas las respuestas: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves an Answer based on the specified UUID identifier.
     *
     * @param id The UUID identifier of the Answer to be retrieved.
     * @return An Answer object if found, or null if not found.
     */
    public Answer findById(UUID id) {
        try {
            Optional<Answer> answer = answerRepository.findById(id);
            return answer.orElse(null);
        } catch (Exception e) {
            logger.error("Error al buscar una respuesta por ID: {}", e.getMessage());
            return null;
        }
    }


    public Answer save(AnswerCreateDTO answerDTO, UUID formActId) {
    try {
        FormAct formAct = formActService.findById(formActId);

        if (answerDTO == null) {
            logger.warn("Se intentó guardar una respuesta nula.");
            return null;
        }

        Answer answer = Answer.builder()
                .id(UUID.fromString(answerDTO.getUuid()))
                .formAct(formAct)
                .date(answerDTO.getDate())
                .uuid(answerDTO.getUuid())
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        logger.info("Respuesta creada con éxito: {}", savedAnswer.getUuid());

        return savedAnswer;
    } catch (Exception e) {
        logger.error("Error al guardar una respuesta: {}", e.getMessage());
        return null;
        }
    }

    /**
     * Deletes an Answer based on the provided AnswerDeleteDTO.
     *
     * @param answerDTO The AnswerDeleteDTO containing information for deleting the Answer.
     * @return The deleted Answer object, or null if an issue occurs.
     */
    public Answer delete(AnswerDeleteDTO answerDTO) {
        try {
            if (answerDTO == null) {
                logger.warn("Se intentó eliminar una respuesta nula.");
                return null;
            }

            Answer answer = Answer.builder()
                    .date(answerDTO.getDate())
                    .formAct(answerDTO.getFormAct())
                    .uuid(answerDTO.getUuid())
                    .build();

            answerRepository.delete(answer);

            logger.info("Respuesta eliminada con éxito: {}", answerDTO.getUuid());

            return answer;
        } catch (Exception e) {
            logger.error("Error al eliminar una respuesta: {}", e.getMessage());
            return null;
        }
    }
}

