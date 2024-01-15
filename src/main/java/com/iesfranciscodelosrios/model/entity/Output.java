package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "outputs")
public class Output {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "formula")
    private String formula;

    @ManyToOne
    @JoinColumn(name = "output_id")
    private UserEntity userOwner;

    @Column(name = "result")
    private String result;
}