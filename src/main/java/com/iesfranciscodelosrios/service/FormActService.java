package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActDeleteDTO;
import com.iesfranciscodelosrios.model.dto.formAct.FormActResponseDTO;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormActService {

    @Autowired
    FormActRepository formActRepository;

    @Autowired
    private FormService formService;

    @Autowired
    private SchoolYearService schoolYearService;

    public FormAct loadFormActByStartDate(LocalDateTime date) {
        Optional<FormAct> formAct = formActRepository.findFormActByStartDate(date);
        return formAct.orElse(null);
    }

    public FormAct findById(UUID id) {
        Optional<FormAct> formAct = formActRepository.findById(id);
        return formAct.orElse(null);
    }

    public Page<FormAct> findAll(Pageable pageable) {
        try {
            return formActRepository.findAll(
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

    public FormAct save(FormActCreateDTO formActCreateDTO) {
        Form form = formService.findById(formActCreateDTO.getFormId());
        SchoolYear schoolYear = schoolYearService.findById(formActCreateDTO.getSchoolYearId());

        if (form == null || schoolYear == null) return null;

        FormAct formAct = FormAct.builder()
                .startDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusHours(1))
                .form(form)
                .schoolYear(schoolYear)
                .build();

        return formActRepository.save(formAct);
    }

    public FormAct delete(FormActDeleteDTO formActDeleteDTO) {
        FormAct formAct = FormAct.builder()
                .id(formActDeleteDTO.getId())
                .build();

        formActRepository.delete(formAct);
        return formAct;
    }

    public FormAct findByForm(Form form) {
        Optional<FormAct> formAct = formActRepository.findByForm(form);
        return formAct.orElse(null);
    }

    public List<FormAct> findAll() {
        return formActRepository.findAll();
    }

    public FormActResponseDTO mapToResponseDTO(FormAct formAct) {
        return FormActResponseDTO.builder()
                .id(formAct.getId())
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .formId(formAct.getId().toString())
                .schoolYearId(formAct.getSchoolYear().getId().toString())
                .answersListId(formAct.getAnswersList() != null ? formAct.getAnswersList().stream().map(answer -> answer.getId().toString()).collect(Collectors.toSet()) : null)
                .build();
    }
}
