package com.iesfranciscodelosrios.model.entity;


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

}
