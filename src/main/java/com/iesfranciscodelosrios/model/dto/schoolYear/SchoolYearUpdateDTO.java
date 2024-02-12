package com.iesfranciscodelosrios.model.dto.schoolYear;

import com.iesfranciscodelosrios.model.entity.FormAct;
import com.iesfranciscodelosrios.model.entity.Institution;
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
public class SchoolYearUpdateDTO {
    private UUID id;
    private String name;
    private UUID institutionId;
    private Set<UUID> formActIdList;
}
