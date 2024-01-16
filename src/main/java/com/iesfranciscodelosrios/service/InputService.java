package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.interfaze.iServices;
import com.iesfranciscodelosrios.repository.InputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InputService implements iServices<Input> {

    @Autowired
    private InputRepository inputRepository;


    public Input findById(UUID id) {
        return inputRepository.findById(id).orElse(null);
    }
    public Input findByName(String name) {
        return inputRepository.findByName(name).orElse(null);
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



}
