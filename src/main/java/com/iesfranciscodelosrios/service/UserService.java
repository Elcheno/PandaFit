package com.iesfranciscodelosrios.service;

import com.iesfranciscodelosrios.model.dto.user.UserUpdateDTO;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Role;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.interfaces.iServices;
import com.iesfranciscodelosrios.model.type.RoleType;
import com.iesfranciscodelosrios.repository.UserRepository;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements iServices<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionService institutionService;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity findById(UUID id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public UserEntity update(UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = UserEntity.builder()
                .id(userUpdateDTO.getId())
                .email(userUpdateDTO.getEmail())
                .password(userUpdateDTO.getPassword())
                .build();

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity delete(UserEntity user) {
        return  null;
    }

    public UserEntity delete(UUID id)  {
        if (id == null) return null;
        try {
           // userRepository.deleteById(id);
            userRepository.forceDelete(id);
        }
        catch (GenericJDBCException a) {
            System.out.println("consulta al fallo");
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        try {
            return  userRepository.findAll(
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

    public Page<UserEntity> findAllByInstitution(UUID institutionId, Pageable pageable) {
        Institution institution = institutionService.findById(institutionId);
        if (institution == null) return null;

        try {
            return userRepository.findAllByInstitution(
                    institution,
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

    public Page<UserEntity> findAllByRole(
            String role,
            Pageable pageable) {

        Role rol;
        try{
            rol = Role.builder()
                    .role(RoleType.valueOf(role))
                    .build();

            System.out.println(rol);
        } catch (IllegalArgumentException e) {
            return null;
        };

        try {
            return userRepository.findAllByRole(
                    role,
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

    public Page<UserEntity> findAllByInstitutionAndRole(
            UUID institutionId,
            String role,
            Pageable pageable) {

        Institution institution = institutionService.findById(institutionId);
        RoleType rol;
        try{
            rol = RoleType.valueOf(role);
        } catch (IllegalArgumentException e) {
            return null;
        };

        if (institution == null) return null;

        System.out.println(pageable.getSort().getOrderFor("email"));

        try {
            return userRepository.findAllByInstitutionAndRole(
                    institution.getId(),
                    role,
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
}
