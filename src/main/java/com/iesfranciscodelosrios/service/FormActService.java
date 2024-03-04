package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.formAct.*;
import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.repository.FormActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormActService {

    @Autowired
    FormActRepository formActRepository;

    @Autowired
    private FormService formService;

    @Autowired
    private SchoolYearService schoolYearService;

    /**
     * Loads a FormAct based on the specified start date.
     *
     * @param date The LocalDateTime representing the start date to search for.
     * @return A FormAct object if found, or null if not found.
     */
    public FormAct loadFormActByStartDate(LocalDateTime date) {
        Optional<FormAct> formAct = formActRepository.findFormActByStartDate(date);
        return formAct.orElse(null);
    }

    /**
     * Retrieves a FormAct based on the specified UUID identifier.
     *
     * @param id The UUID identifier of the FormAct to be retrieved.
     * @return A FormAct object if found, or null if not found.
     */
    public FormAct findById(UUID id) {
        Optional<FormAct> formAct = formActRepository.findById(id);
        return formAct.orElse(null);
    }

    /**
     * Retrieves all FormActs with pagination support.
     *
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing FormAct objects based on the provided Pageable parameters.
     */
    public Page<FormAct> findAll(Pageable pageable) {
        try {
            return formActRepository.findAll(
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

    /**
     * Saves a new FormAct based on the provided FormActCreateDTO.
     *
     * @param formActCreateDTO The FormActCreateDTO containing information for creating the FormAct.
     * @return The created FormAct object, or null if an issue occurs.
     */
    public FormAct save(FormActCreateDTO formActCreateDTO) {
        Form form = formService.findById(formActCreateDTO.getFormId());
        SchoolYear schoolYear = schoolYearService.findById(formActCreateDTO.getSchoolYearId());

        if (form == null || schoolYear == null) return null;

        FormAct formAct = FormAct.builder()
                .startDate(formActCreateDTO.getStartDate())
                .expirationDate(formActCreateDTO.getExpirationDate())
                .form(form)
                .schoolYear(schoolYear)
                .build();

        return formActRepository.save(formAct);
    }

    /**
     * Deletes a FormAct based on the provided FormActDeleteDTO.
     *
     * @param formActDeleteDTO The FormActDeleteDTO containing information for deleting the FormAct.
     * @return The deleted FormAct object.
     */
    public FormAct delete(FormActDeleteDTO formActDeleteDTO) {
        FormAct formAct = FormAct.builder()
                .id(formActDeleteDTO.getId())
                .build();

        formActRepository.delete(formAct);
        return formAct;
    }

    /**
     * Retrieves a FormAct based on the provided Form.
     *
     * @param form The Form object to search for.
     * @return A FormAct object if found, or null if not found.
     */
    public FormAct findByForm(Form form) {
        Optional<FormAct> formAct = formActRepository.findByForm(form);
        return formAct.orElse(null);
    }

    /**
     * Retrieves all FormActs.
     *
     * @return A list of all FormAct objects.
     */
    public List<FormAct> findAll() {
        return formActRepository.findAll();
    }

    /**
     * Retrieves all FormActs for a given school year with pagination support.
     *
     * @param id       The UUID identifier of the school year.
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing FormAct objects based on the provided school year and Pageable parameters.
     */
    public Page<FormAct> findAllBySchoolYear(UUID id, Pageable pageable) {
        try {
            SchoolYear schoolYear = this.schoolYearService.findById(id);
            if (schoolYear == null) return null;
            return formActRepository.findAllBySchoolYear(
                    schoolYear,
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

    /**
     * Retrieves all FormActs for a given school year after the expiration date with pagination support.
     *
     * @param id       The UUID identifier of the school year.
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing FormAct objects based on the provided school year and expiration date criteria.
     */
    public Page<FormAct> findAllBySchoolYearAfterExpirationDate(UUID id, Pageable pageable) {
        try {
            SchoolYear schoolYear = this.schoolYearService.findById(id);
            if (schoolYear == null) return null;
            return formActRepository.findAllBySchoolYearAndExpirationDateAfter(
                    schoolYear,
                    LocalDateTime.now(),
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

    /**
     * Retrieves all FormActs for a given school year before the expiration date with pagination support.
     *
     * @param id       The UUID identifier of the school year.
     * @param pageable The Pageable object specifying the page number, size, and sorting criteria.
     * @return A Page containing FormAct objects based on the provided school year and expiration date criteria.
     */
    public Page<FormAct> findAllBySchoolYearBeforeExpirationDate(UUID id, Pageable pageable) {
        try {
            SchoolYear schoolYear = this.schoolYearService.findById(id);
            if (schoolYear == null) return null;
            return formActRepository.findAllBySchoolYearAndExpirationDateBefore(
                    schoolYear,
                    LocalDateTime.now(),
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

    /**
     * Maps a FormAct object to a FormActResponseDTO object.
     *
     * @param formAct The FormAct object to map.
     * @return The mapped FormActResponseDTO object.
     */
    public FormActResponseDTO mapToResponseDTO(FormAct formAct) {
        return FormActResponseDTO.builder()
                .id(formAct.getId())
                .startDate(formAct.getStartDate())
                .expirationDate(formAct.getExpirationDate())
                .formId(formAct.getForm().getId().toString())
                .formName(formAct.getForm().getName())
                .schoolYearId(formAct.getSchoolYear().getId().toString())
                .answersListId(formAct.getAnswersList() != null ? formAct.getAnswersList().stream().map(answer -> answer.getId().toString()).collect(Collectors.toSet()) : null)
                .build();
    }

    /**
     * Closes a FormAct based on the provided FormActCloseDTO.
     *
     * @param formActCloseDTO The FormActCloseDTO containing information for closing the FormAct.
     * @return The closed FormAct object, or null if not found or an error occurs.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public FormAct closeForm(FormActCloseDTO formActCloseDTO) {
        try {
            FormAct formActToClose = formActRepository.findById(formActCloseDTO.getId()).get();

            if (formActToClose != null){
                formActToClose.setExpirationDate(formActCloseDTO.getExpirationDate());
                return formActRepository.save(formActToClose);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
