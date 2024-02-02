package com.iesfranciscodelosrios.model.dto.form;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormCreateDTO {
    private String name;
    private String description;
    private UserEntity userOwner;
    private Set<FormAct> formActList;

}
