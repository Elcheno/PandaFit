package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.interfaze.iServices;
import com.iesfranciscodelosrios.repository.SchoolYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SchoolYearService implements iServices<SchoolYear> {

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Override
    public SchoolYear findById(UUID id) {
        return schoolYearRepository.findById(id)
                .orElse(null);
    }

    @Override
    public SchoolYear save(SchoolYear schoolYear) {
        return schoolYearRepository.save(schoolYear);
    }

    @Override
    public SchoolYear delete(SchoolYear schoolYear) {
        schoolYearRepository.delete(schoolYear);
        return schoolYear;
    }

    public SchoolYear findByName(String name) {
        return schoolYearRepository.findByName(name)
                .orElse(null);
    }
}
