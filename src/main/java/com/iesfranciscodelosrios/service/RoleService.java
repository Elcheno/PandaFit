package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService implements iServices<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(UUID id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role delete(Role role) {
        roleRepository.delete(role);
        return role;
    }
}
