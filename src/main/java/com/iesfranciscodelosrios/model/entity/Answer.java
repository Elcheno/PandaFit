package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date", unique = true)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "formaAct_id", nullable = false, unique = true)
    private FormAct formAct;

    @Column(name = "uuid", unique = true)
    private String uuid;

}
