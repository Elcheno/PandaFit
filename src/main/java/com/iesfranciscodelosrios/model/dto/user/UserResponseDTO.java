package com.iesfranciscodelosrios.model.dto.user;

import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String password;
    private UUID institutionId;
    private Set<UUID> inputsId;
    private Set<UUID> outputsId;
    private Set<UUID> formsId;
    private Set<RoleType> role;
}
