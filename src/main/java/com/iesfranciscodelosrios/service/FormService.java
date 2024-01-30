package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.form.FormCreateDTO;
import com.iesfranciscodelosrios.model.dto.form.FormDeleteDTO;
import com.iesfranciscodelosrios.model.dto.form.FormResponseDTO;
import com.iesfranciscodelosrios.model.dto.form.FormUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FormService {

    @Autowired
    FormRepository formRepository;

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
                .id(formCreateDTO.getId())
                .name(formCreateDTO.getName())
                // .description(formCreateDTO.getDescription())
                // .otherFields(formCreateDTO.getOtherFields())
                .build();

        return formRepository.save(form);
    }

    public Form update(FormUpdateDTO formUpdateDTO) {
        Form form = Form.builder()
                .id(formUpdateDTO.getId())
                .name(formUpdateDTO.getName())
                .description(formUpdateDTO.getDescription())
                // .otherFields(formUpdateDTO.getOtherFields())
                .build();

        return formRepository.save(form);
    }

    public Form delete(FormDeleteDTO formDeleteDTO) {
        Form form = Form.builder()
        //        .id(formDeleteDTO.getId())
                .name(formDeleteDTO.getName())
                // .description(formDeleteDTO.getDescription())
                // .otherFields(formDeleteDTO.getOtherFields())
                .build();

        formRepository.delete(form);
        return form;
    }

    public FormResponseDTO mapToResponseDTO(Form form) {
        return FormResponseDTO.builder()
                .id(form.getId())
                .name(form.getName())
                .description(form.getDescription())
                .build();
    }
}
