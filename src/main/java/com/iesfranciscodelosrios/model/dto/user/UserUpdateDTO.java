package com.iesfranciscodelosrios.model.dto.user;

import lombok.*;
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
}
