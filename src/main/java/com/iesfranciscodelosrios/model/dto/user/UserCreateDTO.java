package com.iesfranciscodelosrios.model.dto.user;

import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserCreateDTO {
    private String email;

    private String password;

    private Set<String> roles;

    private UUID institutionId;

}
