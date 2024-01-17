package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "school_year")
public class SchoolYear {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    private Institution institution;

    @OneToMany(mappedBy = "schoolYear")
    @EqualsAndHashCode.Exclude
    private Set<FormAct> formActList;

    @Override
    public String toString() {
        return "SchoolYear{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", institution=" + (institution != null ? institution.getName() : null) +
                '}';
    }
}
