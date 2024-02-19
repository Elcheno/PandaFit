package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class FormService {

    @Autowired
    FormRepository formRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormActRepository formActRepository;
    @Autowired
    private InputService inputService;
    @Autowired
    private OutputService outputService;

    private static final Logger logger = LoggerFactory.getLogger(FormService.class);

    public Form loadFormByName(String name) {
        try {
            Form result = formRepository.findByName(name)
                .orElse(null);

            if (result != null){
                logger.info("Buscando formulario por nombre '{}': {}", name, result);
            } else {
                logger.error("No de encontró ningún formualario con el nombre '{}'", name);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar el formulario por nombre '{}': {}", name, e.getMessage());
            throw new RuntimeException("Error al buscar el formulario por nombre:" + e.getMessage());
        }
    }

    public Form findById(UUID id) {
        try {
            Form result = formRepository.findById(id)
                .orElse(null);

            if (result != null){
                logger.info("Buscando formulario por ID '{}': {}", id, result);
            } else {
                logger.error("No se encontró ningún formulario con el ID '{}'", id);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar el formulario por ID '{}': {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el formulario por ID: " + e.getMessage());
        }
    }

    public Page<Form> findAll(Pageable pageable) {
        try {
            logger.info("Buscando todos los formularios.");
            return formRepository.findAll(
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
            logger.error("Error al buscar todos los formularios.\n" + e.getMessage());
            throw new RuntimeException("Error al buscar todos los formularios.\n" + e.getMessage());
        }
    }

    public Form save(FormCreateDTO formCreateDTO) {
        try {
            logger.info("Creando el formulario a partir del DTO: {}", formCreateDTO);

            Set<Input> inputList = new HashSet<>();
            for (UUID id : formCreateDTO.getInputIdList()) {
                inputList.add(inputService.findById(id));
            }

            Set<Output> outputList = new HashSet<>();
            for (UUID id : formCreateDTO.getOutputIdList()) {
                outputList.add(outputService.findById(id));
            }

            Form form = Form.builder()
                    .name(formCreateDTO.getName())
                    .description(formCreateDTO.getDescription())
                    .userOwner(userRepository.findById(formCreateDTO.getUserId()).get())
                    .inputList(inputList)
                    .outputList(outputList)
                    .build();
            logger.info("Guardando formulario: {}", form);
            return formRepository.save(form);
        } catch (Exception e) {
            logger.error("Error al guardar el formulario: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el formulario" + e.getMessage());
        }
    }

    public Form update(FormUpdateDTO formUpdateDTO) {
        try {
            logger.info("Buscando el formulario a actualizar con ID '{}'", formUpdateDTO.getId());
            Form formToUpdate = formRepository.findById(formUpdateDTO.getId()).get();

            if (formToUpdate != null) {

                Set<Input> inputList = new HashSet<>();
                for (UUID id : formUpdateDTO.getInputIdList()) {
                    inputList.add(inputService.findById(id));
                }

                Set<Output> outputList = new HashSet<>();
                for (UUID id : formUpdateDTO.getOutputIdList()) {
                    outputList.add(outputService.findById(id));
                }

                formToUpdate.setName(formUpdateDTO.getName());
                formToUpdate.setDescription(formUpdateDTO.getDescription());
                formToUpdate.setUserOwner(userRepository.findById(formUpdateDTO.getUserId()).get());
                formToUpdate.setInputList(inputList);
                formToUpdate.setOutputList(outputList);

                Set<FormAct> formActList = new HashSet<>();

                for (UUID id : formUpdateDTO.getFormActUidList()) {
                    formActList.add(formActRepository.findById(id).get());
                }

                formToUpdate.setFormActList(formActList);

                logger.info("Actualizando el formulario: {}", formToUpdate);
                return formRepository.save(formToUpdate);
            } else {
                logger.error("El formulario a actualizar con ID '{}' no existe", formUpdateDTO.getId());
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(("Error al actualizar el formulario.\n") + e.getMessage());
        }
    }

    public boolean delete(FormDeleteDTO formDeleteDTO) {
        try {
            Optional<Form> formOptional = formRepository.findById(formDeleteDTO.getId());
            if (formOptional.isPresent()) {
                logger.info("Eliminando el formulario con ID: {}: {}", formDeleteDTO.getId(), formOptional.get());
                formRepository.deleteById(formDeleteDTO.getId());
                return true;
            }
            logger.error("No se pudo eliminar el formulario con ID'{}' : {}", formDeleteDTO.getId(), formOptional.get());
            return false;
        } catch (Exception e) {
            logger.error("Error al eliminar el formulario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el formulario.\n" + e.getMessage());
        }
    }

    public FormResponseDTO mapToResponseDTO(Form form) {
        try {
            logger.info("Creando la response de {}", form);

            Set<UUID> formActUidList = new HashSet<>();
            if(form.getFormActList() != null){
                for (FormAct formAct : form.getFormActList()) {
                    formActUidList.add(form.getId());
                }
            }

            Set<UUID> inputIdList = new HashSet<>();
            if(form.getInputList() != null){
                for (Input input : form.getInputList()) {
                    inputIdList.add(input.getId());
                }
            }

            Set<UUID> outputIdList = new HashSet<>();
            if(form.getOutputList() != null){
                for (Output output : form.getOutputList()) {
                    outputIdList.add(output.getId());
                }
            }

            return FormResponseDTO.builder()
                    .id(form.getId())
                    .name(form.getName())
                    .description(form.getDescription())
                    .userOwner(form.getUserOwner().getId())
                    .formActList(formActUidList)
                    .inputIdList(inputIdList)
                    .outputIdList(outputIdList)
                    .build();

        } catch (Exception e) {
            logger.error("Error al crear la response {}", form);
            throw new RuntimeException("Error al crear la response " + e.getMessage());
        }
    }
}
