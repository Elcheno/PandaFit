package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionDeleteDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class InstitutionService {

    private static final Logger logger = LoggerFactory.getLogger(InstitutionService.class);

    @Autowired
    private InstitutionRepository institutionRepository;

    /**
     * Finds an Institution by its ID.
     *
     * @param id The ID of the Institution to find.
     * @return The found Institution, or null if not found.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Institution findById(UUID id) {
        try {
            Institution result = institutionRepository.findById(id)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando institución por ID '{}': {}", id, result);
            } else {
                logger.info("No se encontró ninguna institución con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar institución por ID '{}': {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar institución por ID: " + e.getMessage());
        }
    }

    /**
     * Saves a new Institution.
     *
     * @param institutionDTO The InstitutionCreateDTO containing information about the Institution to be saved.
     * @return The saved Institution.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Institution save(InstitutionCreateDTO institutionDTO) {
        try {
            if (institutionDTO == null) return null;

            Institution institution = Institution.builder()
                    .name(institutionDTO.getName())
                    .build();

            logger.info("Guardando nueva institución: {}", institution);

            return institutionRepository.save(institution);
        } catch (Exception e) {
            logger.error("Error al guardar la institución: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la institución: " + e.getMessage());
        }
    }

    /**
     * Updates an existing Institution.
     *
     * @param institutionDTO The InstitutionUpdateDTO containing information about the Institution to be updated.
     * @return The updated Institution.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Institution update(InstitutionUpdateDTO institutionDTO) {
        try {
            if (institutionDTO == null) return null;

            Institution institution = Institution.builder()
                    .id(institutionDTO.getId())
                    .name(institutionDTO.getName())
                    .build();

            logger.info("Actualizando la institución con ID {}: {}", institutionDTO.getId(), institution);

            return institutionRepository.save(institution);
        } catch (Exception e) {
            logger.error("Error al actualizar la institución: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar la institución: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing Institution.
     *
     * @param institutionDTO The InstitutionDeleteDTO containing information about the Institution to be deleted.
     * @return The deleted Institution.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Institution delete(InstitutionDeleteDTO institutionDTO) {
        try {
            if (institutionDTO == null) return null;

            Institution institution = Institution.builder()
                    .id(UUID.fromString(String.valueOf(institutionDTO.getId())))
                    .name(institutionDTO.getName())
                    .build();

            logger.info("Eliminando la institución con ID {}: {}", institutionDTO.getId(), institution);

            institutionRepository.delete(institution);

            return institution;
        } catch (Exception e) {
            logger.error("Error al eliminar la institución: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar la institución: " + e.getMessage());
        }
    }

    /**
     * Finds an Institution by its name.
     *
     * @param name The name of the Institution to find.
     * @return The found Institution, or null if not found.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Institution findByName(String name) {
        try {
            Institution result = institutionRepository.findByName(name)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando institución por nombre '{}': {}", name, result);
            } else {
                logger.info("No se encontró ninguna institución con el nombre '{}'", name);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar institución por nombre '{}': {}", name, e.getMessage());
            throw new RuntimeException("Error al buscar institución por nombre: " + e.getMessage());
        }
    }

    /**
     * Retrieves all institutions in a paginated manner.
     *
     * @param pageable Pagination information.
     * @return Page containing the institutions.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Page<Institution> findAll(Pageable pageable) {
        try {
            Page<Institution> result = institutionRepository.findAll(
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

            logger.info("Buscando todas las instituciones paginadas: {}", result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todas las instituciones paginadas: {}", e.getMessage());
            throw new RuntimeException("Error al buscar todas las instituciones paginadas: " + e.getMessage());
        }
    }

    /**
     * Retrieves all institutions whose name contains a given substring in a paginated manner.
     *
     * @param pageable Pagination information.
     * @param name     Substring to search for in institution names.
     * @return Page containing the institutions that match the search criteria.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Page<Institution> findAllByNameContaining(Pageable pageable, String name) {
        try {
            Page<Institution> result = institutionRepository.findAllByNameContainingIgnoreCase(
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
}
