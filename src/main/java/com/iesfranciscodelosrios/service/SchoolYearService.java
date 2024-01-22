package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.SchoolYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (schoolYear == null) return null;
        schoolYearRepository.delete(schoolYear);
        return schoolYear;
    }

    public SchoolYear findByNameAndInstitution(String name, Institution institution) {
        return schoolYearRepository.findByNameAndAndInstitution(name, institution)
                .orElse(null);
    }

    public Page<SchoolYear> findAllByInstitution(Institution institution, Pageable pageable) {
        if (institution == null) return null;
        return schoolYearRepository.findAllByInstitution(institution, pageable);
    }
}
