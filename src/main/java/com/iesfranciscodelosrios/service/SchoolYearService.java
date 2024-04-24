package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearCreateDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearDeleteDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearResponseDTO;
import com.iesfranciscodelosrios.model.dto.schoolYear.SchoolYearUpdateDTO;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import com.iesfranciscodelosrios.repository.SchoolYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SchoolYearService  {

    private static final Logger logger = LoggerFactory.getLogger(SchoolYearService.class);
    @Autowired
    private SchoolYearRepository schoolYearRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private FormActRepository formActRepository;

    /**
     * Retrieves a school year by its ID.
     *
     * @param id ID of the school year to retrieve.
     * @return The school year with the specified ID, or null if not found.
     * @throws RuntimeException If an error occurs during the operation.
     */
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

    /**
     * Saves a new school year.
     *
     * @param schoolYearCreateDTO The DTO containing information about the school year to save.
     * @return The saved school year.
     * @throws RuntimeException If an error occurs during the operation.
     */
    @Transactional
    public SchoolYear save(SchoolYearCreateDTO schoolYearCreateDTO) {
        try {
            logger.info("Creando el año escolar a partir del DTO: {}", schoolYearCreateDTO);
            SchoolYear schoolYear = SchoolYear.builder()
                    .name(schoolYearCreateDTO.getName())
                    .institution(institutionRepository.findById(schoolYearCreateDTO.getInstitutionId()).get())
                    .build();
            logger.info("Guardando el año escolar: {}", schoolYear);
            return schoolYearRepository.save(schoolYear);
        } catch (Exception e) {
            logger.error("Error al guardar el año escolar: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el año escolar: " + e.getMessage());
        }
    }

    /**
     * Deletes a school year.
     *
     * @param schoolYearDeleteDTO The DTO containing information about the school year to delete.
     * @return True if the school year was successfully deleted, false otherwise.
     * @throws RuntimeException If an error occurs during the operation.
     */
    @Transactional
    public boolean delete(SchoolYearDeleteDTO schoolYearDeleteDTO) {
        try {
            Optional<SchoolYear> schoolYearOptional = schoolYearRepository.findById(schoolYearDeleteDTO.getId());
            if (schoolYearOptional.isPresent()){
                logger.info("Eliminando el año escolar con ID '{}' : {}", schoolYearDeleteDTO.getId(), schoolYearOptional.get());
                schoolYearRepository.forceDelete(schoolYearDeleteDTO.getId());
                return true;
            }else{
                logger.error("No se pudo eliminar el año escolar con ID '{}' : {}",schoolYearDeleteDTO.getId(), schoolYearOptional);
                return false;
            }
        }catch (Exception e){
            logger.error("Error al eliminar el formulario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el formulario.\n" + e.getMessage());
        }
    }

    /**
     * Deletes a school year if it's not being used.
     *
     * @param schoolYearDeleteDTO The DTO containing information about the school year to delete.
     * @return True if the school year was successfully deleted, false otherwise.
     * @throws RuntimeException If an error occurs during the operation.
     */
    @Transactional
    public boolean deleteIfNotUse(SchoolYearDeleteDTO schoolYearDeleteDTO) {
        try {
            Optional<SchoolYear> schoolYearOptional = schoolYearRepository.findById(schoolYearDeleteDTO.getId());
            if (schoolYearOptional.isPresent() && schoolYearOptional.get().getFormActList().size() == 0){
                logger.info("Eliminando el año escolar con ID '{}' : {}", schoolYearDeleteDTO.getId(), schoolYearOptional.get());
                schoolYearRepository.forceDelete(schoolYearDeleteDTO.getId());
                return true;
            }else{
                logger.error("No se pudo eliminar el año escolar con ID '{}' : {}",schoolYearDeleteDTO.getId(), schoolYearOptional);
                return false;
            }
        }catch (Exception e){
            logger.error("Error al eliminar el formulario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el formulario.\n" + e.getMessage());
        }
    }

    /**
     * Retrieves a school year by its name and institution.
     *
     * @param name        Name of the school year to retrieve.
     * @param institution Institution of the school year.
     * @return The school year with the specified name and institution, or null if not found.
     * @throws RuntimeException If an error occurs during the operation.
     */
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

    /**
     * Retrieves all school years with names containing the specified string, ignoring case.
     *
     * @param pageable Pageable object for pagination.
     * @param name     String to search for in school year names.
     * @return A page of school years whose names contain the specified string.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public Page<SchoolYear> findAllByNameContaining(Pageable pageable, String name, Institution institution) {

        try {
            Page<SchoolYear> result = schoolYearRepository.findAllByNameContainingIgnoreCaseAndInstitution(
            PageRequest.of(
                    pageable.getPageNumber() > 0
                            ? pageable.getPageNumber()
                            : 0,

                    pageable.getPageSize() > 0
                            ? pageable.getPageSize()
                            : 10

                    //pageable.getSort()
            ), name, institution
            );

            logger.info("Buscando todos los YEARS escolares con el nombre '{}' : {}", name, result);
            return result;
        } catch (Exception e) {
            logger.error("Error al buscar todos los YEARS escolares con el nombre '{}' : {}", name, e.getMessage());
            throw new RuntimeException("Error al buscar todos los YEARS escolares con el nombre: " + e.getMessage());
        }
    }

    /**
     * Retrieves all school years of the specified institution.
     *
     * @param institution Institution to retrieve school years for.
     * @param pageable    Pageable object for pagination.
     * @return A page of school years belonging to the specified institution.
     * @throws RuntimeException If an error occurs during the operation.
     */
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

    /**
     * Updates a school year with information from the provided DTO.
     *
     * @param schoolYearUpdateDTO DTO containing information to update the school year.
     * @return The updated school year.
     * @throws RuntimeException If the school year to update does not exist or an error occurs during the operation.
     */
    public SchoolYear update(SchoolYearUpdateDTO schoolYearUpdateDTO) {
        try {
            logger.info("Buscando el año escolar a actualizar con ID '{}'", schoolYearUpdateDTO.getId());
            SchoolYear schoolYearToUpdate = findById(schoolYearUpdateDTO.getId());

            if (schoolYearToUpdate != null){
                schoolYearToUpdate.setName(schoolYearUpdateDTO.getName());

                Set<FormAct> formActList = new HashSet<>();

                logger.info("Actualizando el año escolar: {}", schoolYearToUpdate);
                return schoolYearRepository.save(schoolYearToUpdate);
            }else {
                logger.error("El año escolar a actualizar con ID '{}' no existe", schoolYearUpdateDTO.getId());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el año escolar: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el año escolar.\n" + e.getMessage());
        }
    }

    /**
     * Maps a SchoolYear entity to a SchoolYearResponseDTO.
     *
     * @param schoolYear The SchoolYear entity to map.
     * @return The mapped SchoolYearResponseDTO.
     * @throws RuntimeException If an error occurs during the mapping process.
     */
    public SchoolYearResponseDTO mapToResponseDTO(SchoolYear schoolYear){
        try{
            logger.info("Creando la response de {}", schoolYear);
            Set<UUID> formActUidList = new HashSet<>();

            if(schoolYear.getFormActList() != null) {
                for (FormAct formAct : schoolYear.getFormActList()) {
                    formActUidList.add(formAct.getId());
                }
            }

            return SchoolYearResponseDTO.builder()
                    .id(schoolYear.getId())
                    .name(schoolYear.getName())
                    .institutionId(schoolYear.getInstitution().getId())
                    .formActIdList(formActUidList)
                    .build();

        } catch (Exception e){
            logger.error("Error al crear la response {}", schoolYear);
            throw new RuntimeException("Error al crear la response " + e.getMessage());
        }
    }
}
