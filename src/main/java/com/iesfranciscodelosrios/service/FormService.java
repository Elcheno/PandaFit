package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
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

    public Form loadFormByName(String name) {
        Optional<Form> form = formRepository.findByName(name);
        return form.orElse(null);
    }

    public Form findById(UUID id) {
        Optional<Form> form = formRepository.findById(id);
        return form.orElse(null);
    }

    public Page<Form> findAll(Pageable pageable) {
        try {
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
            return null;
        }
    }

    public Form save(FormCreateDTO formCreateDTO) {
        Form form = Form.builder()
                .name(formCreateDTO.getName())
                .description(formCreateDTO.getDescription())
                .userOwner(userRepository.findById(formCreateDTO.getUserId()).get())
                .build();

        return formRepository.save(form);
    }

    public Form update(FormUpdateDTO formUpdateDTO) {
        System.out.println(formUpdateDTO);

        Form formToUpdate = formRepository.findById(formUpdateDTO.getId()).get();

        System.out.println(formToUpdate);

        formToUpdate.setName(formUpdateDTO.getName());
        formToUpdate.setDescription(formUpdateDTO.getDescription());
        formToUpdate.setUserOwner(userRepository.findById(formUpdateDTO.getUserId()).get());

        Set<FormAct> formActList = new HashSet<>();

        for (UUID id : formUpdateDTO.getFormActUidList()) {
            formActList.add(formActRepository.findById(id).get());
        }

        formToUpdate.setFormActList(formActList);

        return formRepository.save(formToUpdate);
    }

    public boolean delete(FormDeleteDTO formDeleteDTO) {
        Optional<Form> formOptional = formRepository.findById(formDeleteDTO.getId());
        if (formOptional.isPresent()) {
            formRepository.deleteById(formDeleteDTO.getId());
            return true; // Se eliminó exitosamente
        }
        return false; // Formulario no encontrado
    }

    public FormResponseDTO mapToResponseDTO(Form form) {
        Set<UUID> formActUidList = new HashSet<>();

        if(form.getFormActList() != null){
            for (FormAct formAct : form.getFormActList()) {
                formActUidList.add(form.getId());
            }
        }

        return FormResponseDTO.builder()
                .id(form.getId())
                .name(form.getName())
                .description(form.getDescription())
                .userOwner(form.getUserOwner().getId())
                .formActList(formActUidList)
                .build();
    }
}
