package com.iesfranciscodelosrios.model.dto.output;

import com.iesfranciscodelosrios.model.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputCreateDTO {
    private String name;
    private String description;
    private String formula;
    private UserEntity userOwner;
    private String result;
}
