package com.iesfranciscodelosrios.dao;

import com.iesfranciscodelosrios.model.dto.answer.AnswerQueryDTO;
import com.iesfranciscodelosrios.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AnswerFilterDao {

    private final EntityManager em;

    public List<Answer> managerQuery (List<AnswerQueryDTO> answerQuery) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Answer> criteriaQuery = criteriaBuilder.createQuery(Answer.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Answer> root = criteriaQuery.from(Answer.class);

        answerQuery.forEach(query -> {
            String field = query.getField();
            List<String> content = query.getBody();

            // MULTIPLE
            // FormAct filter query
            if (field != null && field.equals("formAct")) {

                if (content != null && !content.isEmpty()) {
                    List<Predicate> predicateList = new ArrayList<>();

                    content.forEach(value -> {
                        Predicate result = findAllByFormAct(value, root);
                        predicateList.add(result);
                    });

                    Predicate predicateForFormAct = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                    predicates.add(predicateForFormAct);
                }

            }

            // MULTIPLE
            // FormName filter query
            if (field != null && field.equals("formName")) {
                if (content != null && !content.isEmpty()) {
                    List<Predicate> predicateList = new ArrayList<>();

                    content.forEach(value -> {
                        Predicate result = findAllByFormName(value, root);
                        predicateList.add(result);
                    });

                    Predicate predicateForFormName = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                    predicates.add(predicateForFormName);
                }
            }

            // MULTIPLE
            // Institution filter query
            if (field != null && field.equals("institution")) {

                if (content != null && !content.isEmpty()) {
                    List<Predicate> predicateList = new ArrayList<>();

                    content.forEach(value -> {
                        Predicate result = findAllByInstitution(value, root);
                        predicateList.add(result);
                    });

                    Predicate predicateForInstitution = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                    predicates.add(predicateForInstitution);
                }
            }

            // SINGLE
            // UUID filter query
            if (field != null && field.equals("uuid")) {
                if (content != null && content.get(0) != null) {
                    String value = content.get(0);
                    Predicate result = findAllByUuid(value, root);
                    predicates.add(result);
                }
            }

            // RANGE
            // Date filter query
            if (field != null && field.equals("date")) {
                if (content != null && !content.isEmpty()) {
                    try {
                        LocalDateTime startDate = LocalDateTime.parse(content.get(0));
                        LocalDateTime endDate = LocalDateTime.parse(content.get(1));
                        Predicate result = findAllByDateToDate(startDate, endDate, root);
                        predicates.add(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error al filtrar por fecha");
                    }
                }
            }

        });

        Predicate predicateResult = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicateResult);
        TypedQuery<Answer> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Predicate findAllByFormAct (String formActId, Root root) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Predicate formActPredicate = criteriaBuilder.equal(answerFormActJoin.get("id"), UUID.fromString(formActId));
        return formActPredicate;
    }

    public Predicate findAllByFormName (String formName, Root root) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Join<FormAct, Form> formActFormJoin = answerFormActJoin.join("form");
        Predicate formActFormPredicate = criteriaBuilder.equal(formActFormJoin.get("name"), formName);
        return formActFormPredicate;
    }

    public Predicate findAllByInstitution (String institutionId, Root root) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Join<FormAct, SchoolYear> formActSchoolYearJoin = answerFormActJoin.join("schoolYear");
        Join<SchoolYear, Institution> formActInstitutionJoin = formActSchoolYearJoin.join("institution");
        Predicate formInstitutionPredicate = criteriaBuilder.equal(formActInstitutionJoin.get("id"), UUID.fromString(institutionId));
        return formInstitutionPredicate;
    }

    public Predicate findAllByUuid (String uuid, Root root) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Predicate uuidPredicate = criteriaBuilder.equal(root.get("uuid"), uuid);
        return uuidPredicate;
    }

    public Predicate findAllByDateToDate (LocalDateTime startDate, LocalDateTime endDate, Root root) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Predicate datePredicate = criteriaBuilder.between(root.get("date"), startDate, endDate);
        return datePredicate;
    }

}
