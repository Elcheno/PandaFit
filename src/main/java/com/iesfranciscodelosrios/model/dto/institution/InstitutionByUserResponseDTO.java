package com.iesfranciscodelosrios.model.dto.institution;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class InstitutionByUserResponseDTO {
    private UUID id;
    private String name;
    private Set<UUID> schoolYearsId;
    private Set<String> schoolYearsName;
}
