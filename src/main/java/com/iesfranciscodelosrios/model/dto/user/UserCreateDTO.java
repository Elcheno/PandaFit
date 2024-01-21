package com.iesfranciscodelosrios.model.dto.user;

import com.iesfranciscodelosrios.model.entity.*;
import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserCreateDTO {
    private String email;

    private String password;

    private Institution institution;

    private Set<Role> role;

    private Set<Input> inputList;

    private Set<Output> outputList;

    private Set<Form> formList;
}
