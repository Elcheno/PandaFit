package com.iesfranciscodelosrios.dao;

import com.iesfranciscodelosrios.model.dto.answer.AnswerQueryDTO;
import com.iesfranciscodelosrios.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            String type = query.getType();
            List<String> content = query.getBody();

            if (field != null && content != null && !content.isEmpty()) {
                // MULTIPLE
                if (type.equals("multiple")) {
                    List<Predicate> predicateList = new ArrayList<>();
                    content.forEach(value -> predicateList.add(handlerQuery(field, content, root)));
                    predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[0])));
                }

                // SINGLE
                if (type.equals("unique")) {
                    Predicate predicate = handlerQuery(field, content, root);
                    predicates.add(predicate);
                }

                // RANGE
                if (type.equals("range")) {
                    Predicate predicate = handlerQuery(field, content, root);
                    predicates.add(predicate);
                }
            }

        });

        Predicate predicateResult = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(predicateResult);
        TypedQuery<Answer> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Predicate handlerQuery (String field, List<String> values, Root root) {
        try {
            Method method = this.getClass().getDeclaredMethod(field, List.class, Root.class);
            return (Predicate) method.invoke(this, values, root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Predicate formAct (List<String> formActId, Root root) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Predicate formActPredicate = criteriaBuilder.equal(answerFormActJoin.get("id"), UUID.fromString(formActId.get(0)));
        return formActPredicate;
    }

    public Predicate formName (List<String> formName, Root root) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Join<FormAct, Form> formActFormJoin = answerFormActJoin.join("form");
        Predicate formActFormPredicate = criteriaBuilder.equal(formActFormJoin.get("name"), formName.get(0));
        return formActFormPredicate;
    }

    public Predicate institution (List<String> institutionId, Root root) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Join<Answer, FormAct> answerFormActJoin = root.join("formAct");
        Join<FormAct, SchoolYear> formActSchoolYearJoin = answerFormActJoin.join("schoolYear");
        Join<SchoolYear, Institution> formActInstitutionJoin = formActSchoolYearJoin.join("institution");
        Predicate formInstitutionPredicate = criteriaBuilder.equal(formActInstitutionJoin.get("id"), UUID.fromString(institutionId.get(0)));
        return formInstitutionPredicate;
    }

    public Predicate uuid (List<String> uuid, Root root) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Predicate uuidPredicate = criteriaBuilder.equal(root.get("uuid"), uuid.get(0));
        return uuidPredicate;
    }

    public Predicate date (List<String> values, Root root) throws Exception {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        LocalDateTime startDate = LocalDateTime.parse(values.get(0));
        LocalDateTime endDate = LocalDateTime.parse(values.get(1));
        Predicate datePredicate = criteriaBuilder.between(root.get("date"), startDate, endDate);
        return datePredicate;
    }

}
