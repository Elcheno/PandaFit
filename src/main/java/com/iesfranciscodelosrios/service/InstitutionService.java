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

import java.util.UUID;

@Service
public class InstitutionService {

    private static final Logger logger = LoggerFactory.getLogger(InstitutionService.class);

    @Autowired
    private InstitutionRepository institutionRepository;

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
}
