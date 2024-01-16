package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.interfaze.iServices;
import com.iesfranciscodelosrios.repository.FormActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class FormActService implements iServices<FormAct> {
    @Autowired
    FormActRepository formActRepository;

    public FormAct loadFormActByStartDate(LocalDateTime date) {
        Optional<FormAct> formAct = formActRepository.findFormActByStartDate(date);
        return formAct.orElse(null);
    }

    @Override
    public FormAct findById(UUID id) {
        Optional<FormAct> formAct = formActRepository.findById(id);
        return formAct.orElse(null);
    }

    @Override
    public FormAct save(FormAct formAct) {
        return formActRepository.save(formAct);
    }

    @Override
    public FormAct delete(FormAct formAct) {
        formActRepository.delete(formAct);
        return formAct;
    }
}
