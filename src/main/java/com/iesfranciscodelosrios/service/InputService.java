package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.repository.InputRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.slf4j.Logger;


@Service
public class InputService {

    private static final Logger logger = LoggerFactory.getLogger(InputService.class);

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private UserService userService;

    /**
     * Retrieves an Input based on the specified UUID identifier.
     *
     * @param id The UUID identifier of the Input to be retrieved.
     * @return The retrieved Input object, or null if not found.
     * @throws RuntimeException if an error occurs during the retrieval process.
     */
    public Input findById(UUID id) {
        try {
            Input result = inputRepository.findById(id).orElse(null);

            if (result != null) {
                // Registrar información sobre la búsqueda por ID
                logger.info("Buscando input por ID '{}': {}", id, result);
            } else {
                // Si no se encuentra, registrar un mensaje informativo
                logger.info("No se encontró ningún input con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            // Loggear el error antes de lanzar la excepción
            logger.error("Error al buscar input por ID '{}': {}", id, e.getMessage());

            // Lanzar la excepción después de registrar el error
            throw new RuntimeException("Error al buscar input por ID: " + e.getMessage());
        }
    }

    /**
     * Retrieves all Inputs with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing Input objects based on the provided Pageable parameters.
     * @throws RuntimeException if an error occurs during the retrieval process.
     */
    public Page<Input> findAll(Pageable pageable) {
        try {
            Page<Input> result = inputRepository.findAll(
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

            // Registrar información sobre la búsqueda de todos los inputs paginados
            logger.info("Buscando todos los inputs paginados: {}", result);

            return result;
        } catch (Exception e) {
            // Loggear el error antes de lanzar la excepción
            logger.error("Error al buscar todos los inputs paginados: {}", e.getMessage());

            // Lanzar la excepción después de registrar el error
            throw new RuntimeException("Error al buscar todos los inputs paginados: " + e.getMessage());
        }
    }

    /**
     * Saves an Input based on the provided InputCreateDTO.
     *
     * @param inputCreateDTO The InputCreateDTO containing information for creating the Input.
     * @return The saved Input object.
     * @throws RuntimeException if an error occurs during the save process.
     */
    public Input save(InputCreateDTO inputCreateDTO) {
        try {
            UserEntity userOwner = userService.findById(inputCreateDTO.getUserOwnerId());

            Input input = Input.builder()
                    .name(inputCreateDTO.getName())
                    .description(inputCreateDTO.getDescription())
                    .type(inputCreateDTO.getType())
                    .decimal(inputCreateDTO.getDecimal())
                    .decimals(inputCreateDTO.getDecimals())
                    .unit(inputCreateDTO.getUnit())
                    .userOwner(userOwner)
                    .build();

            logger.info("Guardando nuevo input: {}", input);

            return inputRepository.save(input);
        } catch (IllegalArgumentException e) {

            logger.error("Error al guardar el input: {}", e.getMessage());

            throw new RuntimeException("Error al guardar el input: " + e.getMessage());


        }
    }

    /**
     * Updates an Input based on the provided InputUpdateDTO.
     *
     * @param inputUpdateDTO The InputUpdateDTO containing information for updating the Input.
     * @return The updated Input object.
     * @throws RuntimeException if an error occurs during the update process.
     */
    public Input update(InputUpdateDTO inputUpdateDTO) {
        try {
            Input inputToUpdate = findById(inputUpdateDTO.getId());

            Input input = Input.builder()
                    .id(inputUpdateDTO.getId())
                    .name(inputUpdateDTO.getName())
                    .description(inputUpdateDTO.getDescription())
                    .type(inputUpdateDTO.getType())
                    .decimal(inputUpdateDTO.getDecimal())
                    .decimals(inputUpdateDTO.getDecimals())
                    .unit(inputUpdateDTO.getUnit())
                    .userOwner(inputToUpdate.getUserOwner())
                    .build();

            logger.info("Actualizando el input con ID {}: {}", inputUpdateDTO.getId(), input);


            return inputRepository.save(input);
        } catch (DataIntegrityViolationException e) {

            logger.error("Error al actualizar el input: {}", e.getMessage());

            throw new RuntimeException("Error al actualizar el input: " + e.getMessage());
        }
    }

    /**
     * Deletes an Input based on the provided InputDeleteDTO.
     *
     * @param inputDeleteDTO The InputDeleteDTO containing information for deleting the Input.
     * @return The deleted Input object.
     * @throws RuntimeException if an error occurs during the delete process.
     */
    public Input delete(InputDeleteDTO inputDeleteDTO) {
        try {
            UUID id = inputDeleteDTO.getId();

            Input inputToDelete = inputRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Input no encontrado"));

            logger.info("Eliminando el input con ID {}: {}", id, inputToDelete);

            inputRepository.deleteById(id);

            return inputToDelete;
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar el input: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el input: " + e.getMessage());
        }
    }

    /**
     * Retrieves an Input based on the specified name.
     *
     * @param name The name of the Input to be retrieved.
     * @return The retrieved Input object, or null if not found.
     * @throws RuntimeException if an error occurs during the retrieval process.
     */
    public Input findByName(String name) {
        try {
            Input result = inputRepository.findByName(name).orElse(null);

            if (result != null) {
                // Registrar información sobre la búsqueda por nombre
                logger.info("Buscando input por nombre '{}': {}", name, result);
            } else {
                // Si no se encuentra, registrar un mensaje informativo
                logger.info("No se encontró ningún input con el nombre '{}'", name);
            }

            return result;
        } catch (Exception e) {
            // Loggear el error antes de lanzar la excepción
            logger.error("Error al buscar input por nombre '{}': {}", name, e.getMessage());

            // Lanzar la excepción después de registrar el error
            throw new RuntimeException("Error al buscar input por nombre: " + e.getMessage());
        }
    }

}
