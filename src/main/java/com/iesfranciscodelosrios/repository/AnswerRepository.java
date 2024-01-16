package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface AnswerRepository extends CrudRepository<Answer, UUID> {

    Optional<Answer> findAnswerById(UUID id);
}
