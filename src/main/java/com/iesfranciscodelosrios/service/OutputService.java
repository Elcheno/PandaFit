package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.interfaze.iServices;
import com.iesfranciscodelosrios.repository.OutputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OutputService implements iServices<Output> {

    @Autowired
    private OutputRepository outputRepository;


    @Override
    public Output findById(UUID id) {
        return outputRepository.findById(id).orElse(null);
    }

    @Override
    public Output save(Output output) {
        return outputRepository.save(output);
    }

    @Override
    public Output delete(Output output) {
        outputRepository.delete(output);
        return output;
    }

    public Output findByName(String name) {
        return outputRepository.findByName(name).orElse(null);
    }
}
