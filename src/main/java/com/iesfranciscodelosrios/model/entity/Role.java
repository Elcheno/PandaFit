package com.iesfranciscodelosrios.model.entity;

import com.iesfranciscodelosrios.model.type.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "El tipo de rol no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @ManyToMany(mappedBy = "role")
    private Set<UserEntity> userList;

}
