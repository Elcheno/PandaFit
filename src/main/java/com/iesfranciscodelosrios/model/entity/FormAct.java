package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "form_act")
public class FormAct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "startDate")
    private LocalDateTime startDate;

    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @ManyToOne
    @JoinColumn(name = "schoolYear_id", nullable = false)
    private SchoolYear schoolYear;

    @OneToMany(mappedBy = "formAct", fetch = FetchType.EAGER)
    private Set<Answer> answersList;
}
