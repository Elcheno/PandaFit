package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements iServices<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity findById(UUID id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public UserEntity delete(UserEntity userEntity) {
        if (userEntity == null) return null;
        userRepository.delete(userEntity);
        return userEntity;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
