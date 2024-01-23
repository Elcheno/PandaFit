package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.InstitutionRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InstitutionService implements iServices<Institution> {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    public Institution findById(UUID id) {
        return institutionRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Institution save(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Override
    public Institution delete(Institution institution) {
        if (institution == null) return null;
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
