package com.iesfranciscodelosrios.model.dto.output;

import com.iesfranciscodelosrios.model.entity.Input;
import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OutputWithoutOwner {
    private UUID id;

    private String name;

    private String description;

    private String formula;

    private Set<Object> umbralList;

    private Set<UUID> inputsId;

    private String unit;
}
