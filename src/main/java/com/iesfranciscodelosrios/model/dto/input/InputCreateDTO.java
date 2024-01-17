package com.iesfranciscodelosrios.model.dto.input;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputCreateDTO {
    private String name;
    private String description;
    private String validator;
    private UserEntity userOwner;
}
