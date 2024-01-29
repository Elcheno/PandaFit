package com.iesfranciscodelosrios.service;

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
