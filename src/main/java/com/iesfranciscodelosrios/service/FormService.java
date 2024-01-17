package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FormService implements iServices<Form> {

    @Autowired
    FormRepository formRepository;

    public Form loadFormByName(String name) {
        Optional<Form> form = formRepository.findByName(name);
        return form.orElse(null);
    }

    @Override
    public Form findById(UUID id) {
        Optional<Form> form = formRepository.findById(id);
        return form.orElse(null);
    }

    @Override
    public Form save(Form form) {
        return formRepository.save(form);
    }

    @Override
    public Form delete(Form form) {
        formRepository.delete(form);
        return form;
    }
}
