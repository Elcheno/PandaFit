package com.iesfranciscodelosrios.model.dto.formAct;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FormActCloseDTO {
    private UUID id;
    private LocalDateTime expirationDate;
}
