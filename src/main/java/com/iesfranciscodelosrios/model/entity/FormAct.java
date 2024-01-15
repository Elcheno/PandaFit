package com.iesfranciscodelosrios.model.entity;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class FormAct {

    private UUID id;
    private Date fechaInicio;
    private Date fechaExpiracion;

}
