package com.iesfranciscodelosrios.model.entity;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Input {
    private UUID id;
    private String name;
    private String description;
    private String validator;
    private UserEntity userOwner;
}
