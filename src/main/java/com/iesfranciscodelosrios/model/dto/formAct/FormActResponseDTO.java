package com.iesfranciscodelosrios.model.dto.formAct;

import com.iesfranciscodelosrios.model.entity.Answer;
import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.SchoolYear;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FormActResponseDTO {
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String formId;
    private String formName;
    private String schoolYearId;
    private Set<String> answersListId;
}

