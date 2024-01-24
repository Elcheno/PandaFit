package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.InputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InputService implements iServices<Input> {

    @Autowired
    private InputRepository inputRepository;


    public Input findById(UUID id) {
        return inputRepository.findById(id).orElse(null);
    }

    public Page<Input> findAll(Pageable pageable) {
        return inputRepository.findAll(pageable);
    }

    @Override
    public Input save(Input input) {
        return inputRepository.save(input);
    }

    // Este delete de deberá cambiar por un delete if not use
    @Override
    public Input delete(Input input) {
        inputRepository.delete(input);
        return input;
    }

    public Input findByName(String name) {
        return inputRepository.findByName(name).orElse(null);
    }
}
