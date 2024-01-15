package com.iesfranciscodelosrios.model.entity;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Institution {
    private UUID id;
    private String name;
    private List<UserEntity> userList;
    private List<SchoolYear> schoolYearList;
}
