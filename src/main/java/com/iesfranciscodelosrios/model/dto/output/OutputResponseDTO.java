package com.iesfranciscodelosrios.model.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private String formula;

    private Set<Object> umbralList;

    private List<String> inputsId;

    private String unit;
}
