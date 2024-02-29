package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByEmail(email);

        Collection<? extends GrantedAuthority> authorities = new HashSet<>();

        if (userEntity != null) {
            authorities = userEntity.getRole()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())))
                    .collect(Collectors.toSet());
        }

        return new User(
                userEntity != null ? userEntity.getEmail() : "0",
                userEntity != null ? userEntity.getUuid() : "0",
                true,
                true,
                true,
                true,
                authorities
        );
    }

    public UserEntity findByEmail (String email) {
        return userService.findByEmail(email);
    }

    public boolean registerUser (String email, String uuid) {
        UserEntity userEntity = userService.findByEmail(email);

        if (userEntity != null && userEntity.getUuid() == null) {
            UserEntity user = userService.updateUUID(userEntity, uuid);
            if (user.getUuid() == uuid) return true;
        }

        return false;
    }

}
