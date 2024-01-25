package com.iesfranciscodelosrios.model.dto.institution;


import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstitutionUpdateDTO {
    private UUID id;

    private String name;

}
