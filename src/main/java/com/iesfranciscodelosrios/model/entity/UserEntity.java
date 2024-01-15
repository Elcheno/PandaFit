package com.iesfranciscodelosrios.model.entity;


import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserEntity {
    private UUID id;
    private String email;
    private String password;
    private Institution institution;
    private UserEntity userOwner;
    private RoleType role;
}
