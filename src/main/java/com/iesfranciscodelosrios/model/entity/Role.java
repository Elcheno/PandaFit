package com.iesfranciscodelosrios.model.entity;

import com.iesfranciscodelosrios.model.type.RoleType;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Role {
    private UUID id;
    private RoleType role;
}
