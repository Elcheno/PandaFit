package com.iesfranciscodelosrios.model.dto.form;

import com.iesfranciscodelosrios.model.entity.FormAct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormCreateDTO {
    private String name;
    private String description;
    private UUID userId;
    private Set<FormAct> formActList;
}
