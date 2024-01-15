package com.iesfranciscodelosrios.model.entity;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Institution {

    private UUID id;
    private String name;

}
