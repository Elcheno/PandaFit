package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class FormService {

    @Autowired
    FormRepository formRepository;
    @Autowired
    UserRepository userRepository;

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

        Set<FormAct> formActList = formCreateDTO.getFormActList();
        if (formActList != null && !formActList.isEmpty()) {
            for (FormAct formAct : formActList) {
                formAct.setForm(form);
            }
            form.setFormActList(formActList);
        }
        return formRepository.save(form);
    }

    public Form update(FormUpdateDTO formUpdateDTO) {
        Form formToUpdate = formRepository.findById(formUpdateDTO.getId()).get();

        formToUpdate.setName(formUpdateDTO.getName());
        formToUpdate.setDescription(formUpdateDTO.getDescription());
        formToUpdate.setUserOwner(formUpdateDTO.getUserOwner());
        formToUpdate.setFormActList(formUpdateDTO.getFormActList());

        return formRepository.save(formToUpdate);
    }

    public Form delete(FormDeleteDTO formDeleteDTO) {
        Form form = Form.builder()
                .id(formDeleteDTO.getId())
                .build();

        formRepository.delete(form);
        return form;
    }

    public FormResponseDTO mapToResponseDTO(Form form) {
        return FormResponseDTO.builder()
                .id(form.getId())
                .name(form.getName())
                .description(form.getDescription())
                .userOwner(form.getUserOwner())
                .formActList(form.getFormActList())
                .build();
    }
}
