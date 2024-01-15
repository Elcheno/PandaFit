package com.iesfranciscodelosrios.model.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Answer {

    private UUID id;
    private LocalDate date;
    private Form form;
    private String uuid;

}
