package com.iesfranciscodelosrios.model.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerCreateDTO {
    private UUID id;
    private LocalDateTime date;
    private String uuid;
}
