package com.iesfranciscodelosrios.model.dto.UserEntity;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.Input;
import com.iesfranciscodelosrios.model.entity.Institution;
import com.iesfranciscodelosrios.model.entity.Output;
import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserUpdateDTO {
    private UUID id;

    private String email;

    private String password;

    private Institution institution;

    private Set<RoleType> role;

    private Set<Input> inputList;

    private Set<Output> outputList;

    private Set<Form> formList;
}
