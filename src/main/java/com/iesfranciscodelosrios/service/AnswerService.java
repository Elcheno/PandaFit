package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerResponseDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputWithoutOwner;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerService{

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    FormActService formActService;

    @Autowired
    FormService formService;

    @Autowired
    SchoolYearService schoolYearService;

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
     * Retrieves all Answers by FormAct with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing Answer objects based on the provided Pageable parameters.
     */
    public Page<Answer> findAllByFormAct(UUID formActId, Pageable pageable) {
        try {

            FormAct formAct = formActService.findById(formActId);
            if (formAct == null) return null;

            return answerRepository.findAllByFormAct(
                    formAct,
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
            logger.error("Error al buscar todas las respuestas de un formulario activo: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all Answers by Form with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing Answer objects based on the provided Pageable parameters.
     */
    public Page<Answer> findAllByForm(UUID formId, Pageable pageable) {
        try {

            Form form = formService.findById(formId);
            if (form == null) return null;

            return answerRepository.findAllByFormAct_Form(
                    form,
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
            logger.error("Error al buscar todas las respuestas de un formulario: {}", e.getMessage());
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

    /**
     * Saves the answer provided in the AnswerCreateDTO object.
     *
     * @param answerCreateDTO The AnswerCreateDTO object containing the information for creating the answer.
     * @return The saved Answer object.
     * @throws RuntimeException If an error occurs while creating the answer.
     */
    public Answer save(AnswerCreateDTO answerCreateDTO) {
        try {
            logger.info("Guardando la respuesta: {}", answerCreateDTO);
            FormAct formAct = formActService.findById(answerCreateDTO.getFormActId());

            if (formAct == null) {
                logger.error("El formulario activo no existe: {}", answerCreateDTO.getFormActId());
                throw new RuntimeException("El formulario activo no existe");
            }

            Set<Object> outputs = new HashSet<>();
            formAct.getForm().getOutputList().forEach(output -> {
                OutputWithoutOwner outputWithoutOwner = OutputWithoutOwner.builder()
                        .id(output.getId())
                        .name(output.getName())
                        .formula(output.getFormula())
                        .description(output.getDescription())
                        .umbralList(output.getUmbralList())
                        .inputsId(output.getInputs().stream().map(input -> input.getId()).collect(Collectors.toSet()))
                        .unit(output.getUnit())
                        .build();
                outputs.add(outputWithoutOwner);
            });


            Answer answer = Answer.builder()
                    .date(answerCreateDTO.getDate())
                    .formAct(formAct)
                    .uuid(answerCreateDTO.getUuid())
                    .response(answerCreateDTO.getResponse())
                    .outputs(outputs)
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

    /**
     * Retrieves a list of Answers based on a substring of the uuid.
     *
     * @param uuid The substring to search in the uuid field.
     * @return The list of retrieved Answer objects, or an empty list if not found.
     */
    public Page<Answer> findAllByUuid(Pageable pageable, UUID schoolyearId, String uuid) {
        try {
            SchoolYear schoolYear = schoolYearService.findById(schoolyearId);

            Page<Answer> result = answerRepository.findAllByFormAct_SchoolYearAndUuidContainingIgnoreCase(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10
                    ), schoolYear, uuid
            );

            logger.info("Buscando todos los answers por uuid: {}", result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los answers por uuid: {}", e.getMessage());
            throw new RuntimeException("Error al buscar todos los answers por uuid: " + e.getMessage());
        }
    }

    /**
     * Maps an Answer object to an AnswerResponseDTO object.
     *
     * @param answer The Answer object to map.
     * @return The mapped AnswerResponseDTO object.
     * @throws RuntimeException If an error occurs while creating the response DTO.
     */
        public AnswerResponseDTO mapToResponseDTO (Answer answer){
            try {
                logger.info("Creando la response de {}", answer);

                return AnswerResponseDTO.builder()
                        .id(answer.getId())
                        .date(answer.getDate())
                        .response(answer.getResponse())
                        .outputs(answer.getOutputs())
                        .formActId(answer.getFormAct().getId())
                        .uuid(answer.getUuid())
                        .build();

            } catch (Exception e) {
                logger.error("Error al crear la response de la respuesta: {}", e.getMessage());
                throw new RuntimeException("Error al crear la response de la respuesta.\n" + e.getMessage());
            }
        }
    }
