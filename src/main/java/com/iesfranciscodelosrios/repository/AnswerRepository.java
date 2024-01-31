package com.iesfranciscodelosrios.repository;

import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface AnswerRepository extends CrudRepository<Answer, UUID> {
    @Query(
            value = "SELECT * FROM answer a WHERE a.date = ?1",
            nativeQuery = true)
    Optional<Answer> findAnswerByDate(LocalDateTime date);

    Page<Answer> findAll(Pageable pageable) throws Exception;
}
