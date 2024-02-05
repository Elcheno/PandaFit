package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearDeleteDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.SchoolYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SchoolYearService  {

    private static final Logger logger = LoggerFactory.getLogger(SchoolYearService.class);

    @Autowired
    private SchoolYearRepository schoolYearRepository;


    public SchoolYear findById(UUID id) {
        try {
            SchoolYear result = schoolYearRepository.findById(id)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando año escolar por ID '{}': {}", id, result);
            } else {
                logger.info("No se encontró ningún año escolar con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar año escolar por ID '{}': {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar año escolar por ID: " + e.getMessage());
        }
    }


    public SchoolYear save(SchoolYear schoolYear) {
        try {
            logger.info("Guardando nuevo año escolar: {}", schoolYear);
            return schoolYearRepository.save(schoolYear);
        } catch (Exception e) {
            logger.error("Error al guardar el año escolar: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el año escolar: " + e.getMessage());
        }
    }


    public SchoolYear delete(SchoolYearDeleteDTO schoolYearDeleteDTO) {
        try {
            if (schoolYearDeleteDTO == null) return null;

            SchoolYear schoolYear = SchoolYear.builder()
                            .id(UUID.fromString(String.valueOf(schoolYearDeleteDTO.getId())))
                            .build();

            logger.info("Eliminando el año escolar con ID {}: {}", schoolYearDeleteDTO.getId(), schoolYear);

            schoolYearRepository.delete(schoolYear);

            return schoolYear;
        } catch (Exception e) {
            logger.error("Error al eliminar el año escolar: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el año escolar: " + e.getMessage());
        }
    }

    public SchoolYear findByNameAndInstitution(String name, Institution institution) {
        try {
            SchoolYear result = schoolYearRepository.findByNameAndAndInstitution(name, institution)
                    .orElse(null);

            if (result != null) {
                logger.info("Buscando año escolar por nombre '{}' e institución '{}': {}", name, institution, result);
            } else {
                logger.info("No se encontró ningún año escolar con el nombre '{}' e institución '{}'", name, institution);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar año escolar por nombre '{}' e institución '{}': {}", name, institution, e.getMessage());
            throw new RuntimeException("Error al buscar año escolar por nombre e institución: " + e.getMessage());
        }
    }

    public Page<SchoolYear> findAllByInstitution(Institution institution, Pageable pageable) {
        try {
            if (institution == null) return null;

            Page<SchoolYear> result = schoolYearRepository.findAllByInstitution(institution, pageable);

            logger.info("Buscando todos los años escolares de la institución '{}': {}", institution, result);

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los años escolares de la institución '{}': {}", institution, e.getMessage());
            throw new RuntimeException("Error al buscar todos los años escolares de la institución: " + e.getMessage());
        }
    }
}
