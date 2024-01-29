package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService implements iServices<Answer> {
    @Autowired
    AnswerRepository answerRepository;

    public Answer loadAnswerByDate(LocalDateTime date) {
        Optional<Answer> answer = answerRepository.findAnswerByDate(date);
        return answer.orElse(null);
    }

    public Page<Answer> findAll(Pageable pageable) {
        try {
            return answerRepository.findAll(
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
    @Override
    public Answer findById(UUID id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElse(null);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer delete(Answer answer) {
        answerRepository.delete(answer);
        return answer;
    }
}

