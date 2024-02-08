package com.iesfranciscodelosrios.model.dto.input;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import com.iesfranciscodelosrios.model.type.TypeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputCreateDTO {
    private String name;

    private String description;

    private TypeType type;

    private Boolean decimal;

    private Number decimals;

    private String unit;

    private UUID userOwnerId;


}
