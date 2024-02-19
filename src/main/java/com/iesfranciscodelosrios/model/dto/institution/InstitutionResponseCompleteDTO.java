package com.iesfranciscodelosrios.model.dto.institution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstitutionResponseCompleteDTO {
    private UUID id;

    private String name;

    private Integer schoolYearQuantity;

    private Integer userQuantity;
}
