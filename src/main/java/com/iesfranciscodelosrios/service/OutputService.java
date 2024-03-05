package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.OutputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OutputService {

    @Autowired
    private OutputRepository outputRepository;

    @Autowired
    private InputService inputService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(OutputService.class);


    /*public Output findById(UUID id) {
        return outputRepository.findById(id).orElse(null);
    }*/

    /**
     * Retrieves an Output based on the specified UUID identifier.
     *
     * @param id The UUID identifier of the Output to be retrieved.
     * @return The retrieved Output object, or null if not found.
     */
    public Output findById(UUID id) {
        try {
            Output result = outputRepository.findById(id).orElse(null);

            if (result != null) {
                logger.info("Buscando output por ID '{}': {}", id, result);
            } else {
                logger.info("No se encontró ningún output con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar output por ID '{}': {}", id, e.getMessage());

            throw new RuntimeException("Error al buscar output por ID: " + e.getMessage());
        }
    }

    /**
     * Retrieves all Outputs with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing Output objects based on the provided Pageable parameters.
     */
    public Page<Output> findAll(Pageable pageable) {
        try {
            Page<Output> result = outputRepository.findAll(
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

            logger.info("Buscando todos los outputs paginados: {}", result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los outputs paginados: {}", e.getMessage());

            throw new RuntimeException("Error al buscar todos los outputs paginados: " + e.getMessage());
        }
    }

    /**
     * Retrieves all Outputs containing the given name with pagination.
     *
     * @param pageable Pagination information.
     * @param name The name to search for.
     * @return A Page containing Output objects.
     */
    public Page<Output> findAllByNameContaining(Pageable pageable, String name) {
        try {
            Page<Output> result = outputRepository.findAllByNameContainingIgnoreCase(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,

                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10
                    ), name
            );

            logger.info("Buscando todas las instituciones paginadas: {}", result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todas las instituciones paginadas: {}", e.getMessage());
            throw new RuntimeException("Error al buscar todas las instituciones paginadas: " + e.getMessage());
        }
    }


    /**
     * Saves an Output based on the provided OutputCreateDTO.
     *
     * @param outputCreateDTO The OutputCreateDTO containing information for creating the Output.
     * @return The saved Output object.
     * @throws RuntimeException if an error occurs during the save process.
     */
    public Output save(OutputCreateDTO outputCreateDTO) {
        try {
            UserEntity userOwner = userService.findById(outputCreateDTO.getUserOwnerId());

            Output output = Output.builder()
                    .name(outputCreateDTO.getName())
                    .description(outputCreateDTO.getDescription())
                    .formula(outputCreateDTO.getFormula())
                    .userOwner(userOwner)
                    .umbralList(outputCreateDTO.getUmbralList())
                    .inputs(outputCreateDTO.getInputsId().stream().map((item) -> this.inputService.findById(UUID.fromString(item))).toList())
                    .unit(outputCreateDTO.getUnit())
                    .build();

            Output savedOutput = outputRepository.save(output);

            logger.info("Output guardado con éxito: {}", savedOutput);

            return savedOutput;
        } catch (IllegalArgumentException e) {
            logger.error("Error al guardar el output: {}", e.getMessage());

            throw new RuntimeException("Error al guardar el output: " + e.getMessage());
        }
    }

    /**
     * Updates an Output based on the provided OutputUpdateDTO.
     *
     * @param outputUpdateDTO The OutputUpdateDTO containing information for updating the Output.
     * @return The updated Output object.
     * @throws RuntimeException if an error occurs during the update process.
     */
    public Output update(OutputUpdateDTO outputUpdateDTO) {
        try {
            if (outputUpdateDTO.getId() == null) {
                logger.error("Error al actualizar el output: El campo 'id' no puede ser nulo");
                throw new IllegalArgumentException("El campo 'id' no puede ser nulo para la actualización");
            }

            Output output = Output.builder()
                    .id(outputUpdateDTO.getId())
                    .name(outputUpdateDTO.getName())
                    .description(outputUpdateDTO.getDescription())
                    .formula(outputUpdateDTO.getFormula())
                    .build();

            Output updatedOutput = outputRepository.save(output);

            logger.info("Output actualizado con éxito: {}", updatedOutput);

            return updatedOutput;
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar el output: {}", e.getMessage());

            throw new RuntimeException("Error al actualizar el output: " + e.getMessage());
        }
    }


    /**
     * Deletes an Output based on the provided OutputDeleteDTO.
     *
     * @param outputDeleteDTO The OutputDeleteDTO containing information for deleting the Output.
     * @return The deleted Output object.
     * @throws RuntimeException if an error occurs during the delete process.
     */
    public Output delete(OutputDeleteDTO outputDeleteDTO) {
        try {
            UUID id = outputDeleteDTO.getId();

            Output outputToDelete = outputRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Error al eliminar el output: Output no encontrado");
                        return new IllegalArgumentException("Output no encontrado");
                    });

            outputRepository.deleteById(id);

            logger.info("Output eliminado con éxito: {}", outputToDelete);

            return outputToDelete;
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar el output: {}", e.getMessage());

            throw new RuntimeException("Error al eliminar el output: " + e.getMessage());
        }
    }

    /**
     * Retrieves an Output based on the specified name.
     *
     * @param name The name of the Output to be retrieved.
     * @return The retrieved Output object, or null if not found.
     */
    public Output findByName(String name) {
        try {
            return outputRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            logger.error("Error al buscar el output por nombre: {}", e.getMessage());
            return null;
        }
    }
}
