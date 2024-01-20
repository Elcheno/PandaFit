package com.iesfranciscodelosrios.model.dto.schoolYear;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolYearCreateDTO {

    private String name;

    private Institution institution;

    private Set<FormAct> formActList;

}