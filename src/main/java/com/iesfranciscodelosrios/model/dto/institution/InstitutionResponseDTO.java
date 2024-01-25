package com.iesfranciscodelosrios.model.dto.institution;

import com.iesfranciscodelosrios.model.entity.SchoolYear;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstitutionResponseDTO {

    private UUID id;

    private String name;

}
