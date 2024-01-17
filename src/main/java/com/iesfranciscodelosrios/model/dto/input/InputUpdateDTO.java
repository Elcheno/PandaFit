package com.iesfranciscodelosrios.model.dto.input;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputUpdateDTO {
    private UUID id;
    private String name;
    private String description;
    private String validator;
    private UserEntity userOwner;
}
