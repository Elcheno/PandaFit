package com.iesfranciscodelosrios.model.dto.form;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.UserEntity;
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
public class FormResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID userOwner;
    private Set<UUID> formActList;
    private Set<UUID> outputIdList;
    private Set<UUID> inputIdList;
}
