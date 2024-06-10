package com.iesfranciscodelosrios.model.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerQueryDTO {
    private String type;
    private String field;
    private List<String> body;
}
