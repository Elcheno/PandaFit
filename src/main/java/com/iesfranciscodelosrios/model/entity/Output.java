package com.iesfranciscodelosrios.model.entity;

import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Output {
    private int id;
    private String name;
    private String description;
    private String formula;
}