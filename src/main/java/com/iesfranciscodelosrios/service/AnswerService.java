package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.answer.AnswerCreateDTO;
import com.iesfranciscodelosrios.model.dto.answer.AnswerDeleteDTO;
import com.iesfranciscodelosrios.model.entity.Answer;
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
public class AnswerService{
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
    public Answer findById(UUID id) {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElse(null);
    }

    public Answer save(AnswerCreateDTO answerDTO) {
        if(answerDTO == null) return null;

        Answer answer = Answer.builder()
                .id(UUID.fromString(answerDTO.getUuid()))
                .date(answerDTO.getDate())
                .uuid(answerDTO.getUuid())
                .build();
        return answerRepository.save(answer);
    }

    public Answer delete(AnswerDeleteDTO answerDTO) {
        if(answerDTO == null) return null;

        Answer answer = Answer.builder()
                .date(answerDTO.getDate())
                .formAct(answerDTO.getFormAct())
                .uuid(answerDTO.getUuid())
                .build();
        answerRepository.delete(answer);
        return answer;
    }
}

