package com.iesfranciscodelosrios.model.dto.output;

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
public class OutputUpdateDTO {
    private UUID id;
    private String name;

    private String result;
}
