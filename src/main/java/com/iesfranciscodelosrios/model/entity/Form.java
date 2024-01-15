package com.iesfranciscodelosrios.model.entity;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Form {

private UUID id;
private String name;
private String description;
private UserEntity admin;
private List<Answer> answers;

}
