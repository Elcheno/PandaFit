package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.input.InputCreateDTO;
import com.iesfranciscodelosrios.model.dto.input.InputDeleteDTO;
import com.iesfranciscodelosrios.model.dto.input.InputUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.InputRepository;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private UserRepository userRepository;


    public Input findById(UUID id) {
        return inputRepository.findById(id).orElse(null);
    }

    public Page<Input> findAll(Pageable pageable) {
        try {
            return inputRepository.findAll(
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



    public Input save(InputCreateDTO inputCreateDTO) {
        // ...
        UserEntity userOwner = userRepository.findById(inputCreateDTO.getUserOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Propietario no encontrado"));

        Input input = Input.builder()
                .name(inputCreateDTO.getName())
                .description(inputCreateDTO.getDescription())
                .validator(inputCreateDTO.getValidator())
                .userOwner(userOwner)
                .build();

        return inputRepository.save(input);
    }

    public Input update(InputUpdateDTO inputUpdateDTO) {
        Input input = Input.builder()
                .id(inputUpdateDTO.getId())
                .name(inputUpdateDTO.getName())
        //        .description(inputUpdateDTO.getDescription())
        //        .validator(inputUpdateDTO.getValidator())
        //        .userOwner(inputUpdateDTO.getUserOwner())
                .build();

        return inputRepository.save(input);
    }


    // Este delete de deberá cambiar por un delete if not use
    public Input delete(InputDeleteDTO inputDeleteDTO) {

        Input input = Input.builder()
                .id(inputDeleteDTO.getId())
                .name(inputDeleteDTO.getName())
        //        .description(inputDeleteDTO.getDescription())
        //        .validator(inputDeleteDTO.getValidator())
        //        .userOwner(inputDeleteDTO.getUserOwner())
                .build();

        return inputRepository.save(input);
    }

    public Input findByName(String name) {
        return inputRepository.findByName(name).orElse(null);
    }
}
