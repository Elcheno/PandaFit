package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.output.OutputCreateDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.output.OutputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.OutputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OutputService {

    @Autowired
    private OutputRepository outputRepository;

    @Autowired
    private UserRepository userRepository;


    public Output findById(UUID id) {
        return outputRepository.findById(id).orElse(null);
    }


    public Page<Output> findAll(Pageable pageable) {
        try {
            return outputRepository.findAll(
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

    public Output save(OutputCreateDTO outputCreateDTO) {

        UserEntity userOwner = userRepository.findById(outputCreateDTO.getUserOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Propietario no encontrado"));
        Output output = Output.builder()
                .name(outputCreateDTO.getName())
                .description(outputCreateDTO.getDescription())
                .formula(outputCreateDTO.getFormula())
                .userOwner(userOwner)
                .build();

        return outputRepository.save(output);
    }

    public Output update(OutputUpdateDTO outputUpdateDTO) {
        Output output = Output.builder()
                .id(outputUpdateDTO.getId())
                .name(outputUpdateDTO.getName())
                //        .description(outputUpdateDTO.getDescription())
                //        .formula(outputUpdateDTO.getFormula())
                //        .userOwner(outputUpdateDTO.getUserOwner())
                .build();

        return outputRepository.save(output);
    }

    public Output delete(OutputDeleteDTO outputDeleteDTO) {
        Output output = Output.builder()
                .id(outputDeleteDTO.getId())
                .name(outputDeleteDTO.getName())
                //        .description(outputDeleteDTO.getDescription())
                //        .formula(outputDeleteDTO.getFormula())
                //        .userOwner(outputDeleteDTO.getUserOwner())
                .build();

        return outputRepository.save(output);
    }

    public Output findByName(String name) {
        return outputRepository.findByName(name).orElse(null);
    }
}
