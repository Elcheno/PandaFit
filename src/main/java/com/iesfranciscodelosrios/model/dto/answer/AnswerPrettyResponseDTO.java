package com.iesfranciscodelosrios.model.dto.answer;

import com.iesfranciscodelosrios.model.entity.Form;
import com.iesfranciscodelosrios.model.entity.FormAct;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerPrettyResponseDTO {
    private UUID id;
    private LocalDateTime date;
    private UUID formActId;
    private String formName;
    private String uuid;
    private Set<Object> response;
}
