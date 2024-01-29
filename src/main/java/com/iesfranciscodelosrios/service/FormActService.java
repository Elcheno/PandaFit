package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.formAct.FormActCreateDTO;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FormActService implements iServices<FormAct> {

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

    @Override
    public FormAct findById(UUID id) {
        Optional<FormAct> formAct = formActRepository.findById(id);
        return formAct.orElse(null);
    }


    /**
     * @Deprecated
     * @param formAct
     * @return
     */
    @Override
    public FormAct save(FormAct formAct) {
        return formActRepository.save(formAct);
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

    @Override
    public FormAct delete(FormAct formAct) {
        if (formAct == null) return null;
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
}
