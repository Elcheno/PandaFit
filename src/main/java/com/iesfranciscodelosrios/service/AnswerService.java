package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerResponseDTO;
import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import com.iesfranciscodelosrios.repository.FormActRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService{
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    FormActRepository formActRepository;

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    /**
     * Loads an Answer based on the specified date.
     *
     * @param date The LocalDateTime representing the date to search for.
     * @return An Answer object if found, or null if not found.
     */
    public Answer loadAnswerByDate(LocalDateTime date) {
        try {
            Answer result = answerRepository.findAnswerByDate(date)
                    .orElse(null);

            if (result != null){
                logger.info("Buscando respuesta por fecha '{}': {}", date, result);
            } else {
                logger.error("No de encontró respuesta por fecha '{}'", date);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar la respuesta por fecha: '{}' : {}", date , e.getMessage());
            throw new RuntimeException("Error al buscar la respuesta por fecha: '" + date + "' : " + e.getMessage());
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
            Answer result = answerRepository.findById(id)
                    .orElse(null);

            if (result != null){
                logger.info("Buscando respuesta por ID '{}': {}", id, result);
            } else {
                logger.error("No se encontró ninguna respuesta con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar una respuesta por ID: {}", e.getMessage());
            return null;
        }
    }


    public Answer save(AnswerCreateDTO answerCreateDTO) {
        try {
            logger.info("Guardando la respuesta: {}", answerCreateDTO);

            Answer answer = Answer.builder()
                    .date(answerCreateDTO.getDate())
                    .formAct(formActRepository.findById(answerCreateDTO.getFormActId()).get())
                    .uuid(answerCreateDTO.getUuid())
                    .response(answerCreateDTO.getResponse())
                    .build();

            logger.info("Guardando la respuesta: {}", answer);
            return answerRepository.save(answer);
        } catch (Exception e) {
            logger.error("Error al crear la respuesta: {}", e.getMessage());
            throw new RuntimeException("Error al crear la respuesta.\n" + e.getMessage());
        }
    }

        /**
         * Deletes an Answer based on the provided AnswerDeleteDTO.
         *
         * @param answerDeleteDTO The AnswerDeleteDTO containing information for deleting the Answer.
         * @return The deleted Answer object, or null if an issue occurs.
         */
        @Transactional
        public boolean delete (AnswerDeleteDTO answerDeleteDTO){
            try {
                Optional<Answer> answerOpotional = answerRepository.findById(answerDeleteDTO.getId());
                if (answerOpotional.isPresent()) {
                    logger.info("Eliminando la respuesta con ID: {}: {}", answerDeleteDTO.getId(), answerOpotional.get());
                    answerRepository.forceDelete(answerOpotional.get().getId());
                    return true;
                }
                logger.error("No se pudo eliminar la respuesta con ID'{}' : {}", answerDeleteDTO.getId(), answerOpotional.get());
                return false;
            } catch (Exception e) {
                logger.error("Error al eliminar la respuesta: {}", e.getMessage());
                throw new RuntimeException("Error al eliminar la respuesta.\n" + e.getMessage());
            }
        }

        public AnswerResponseDTO mapToResponseDTO (Answer answer){
            try {
                logger.info("Creando la response de {}", answer);

                return AnswerResponseDTO.builder()
                        .id(answer.getId())
                        .date(answer.getDate())
                        .response(answer.getResponse())
                        .formActId(answer.getFormAct().getId())
                        .uuid(answer.getUuid())
                        .build();

            } catch (Exception e) {
                logger.error("Error al crear la response de la respuesta: {}", e.getMessage());
                throw new RuntimeException("Error al crear la response de la respuesta.\n" + e.getMessage());
            }
        }
    }
