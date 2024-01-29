package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.institution.InstitutionCreateDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionDeleteDTO;
import com.iesfranciscodelosrios.model.dto.institution.InstitutionUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InstitutionService{

    @Autowired
    private InstitutionRepository institutionRepository;

    public Institution findById(UUID id) {
        return institutionRepository.findById(id)
                .orElse(null);
    }

    public Institution save(InstitutionCreateDTO institutionDTO) {
        if (institutionDTO == null) return null;

        Institution institution = Institution.builder()
                .name(institutionDTO.getName())
                .build();
        return institutionRepository.save(institution);
    }

    public Institution update(InstitutionUpdateDTO institutionDTO) {
        if (institutionDTO == null) return null;

        Institution institution = Institution.builder()
                .id(institutionDTO.getId())
                .name(institutionDTO.getName())
                .build();
        return institutionRepository.save(institution);
    }

    public Institution delete(InstitutionDeleteDTO institutionDTO) {
        if (institutionDTO == null) return null;
        Institution institution = Institution.builder()
                .id(UUID.fromString(String.valueOf(institutionDTO.getId())))
                .name(institutionDTO.getName())
                .build();
        institutionRepository.delete(institution);
        return institution;
    }

    public Institution findByName(String name) {
        return institutionRepository.findByName(name)
                .orElse(null);
    }

    public Page<Institution> findAll(Pageable pageable) {
        try {
            return institutionRepository.findAll(
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
}
