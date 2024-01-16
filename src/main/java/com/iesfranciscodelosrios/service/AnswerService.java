package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AnswerService{
    @Autowired
    AnswerRepository answerRepository;

    public Answer loadAnswerByDate(LocalDate date) {
        Optional<Answer> answer = answerRepository.findAnswerByDate(date);
        return answer.orElse(null);
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteAnswer(Answer answer) {
        answerRepository.delete(answer);
    }
}

