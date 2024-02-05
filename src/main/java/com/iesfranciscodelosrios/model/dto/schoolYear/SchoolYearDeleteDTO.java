package com.iesfranciscodelosrios.model.dto.schoolYear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolYearDeleteDTO {

    private UUID id;

}
