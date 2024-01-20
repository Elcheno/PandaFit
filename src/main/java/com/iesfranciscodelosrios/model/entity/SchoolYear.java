package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El nombre no puede estar en blanco")
    // Dígito entre 1 y 4 seguido de "º", espacio en blanco, seguido de ESO o BACHILLERATO, espacio en blanco, grupo capturador que acepta una o más palabras
    // la cual cada una comienza con una letra mayúscula, espacio en blanco y por último una letra en mayúscula
    @Pattern(regexp = "^[1-4]º\\s(?:ESO|BACHILLERATO)\\s(?:[A-Z]+(?:\\s[A-Z]+)?)\\s(?:[A-Z])$", message = "El formato del nombre debe ser como '1º ESO C', '2º BACHILLERATO CIENCIAS SOCIALES A', '3º BACHILLERATO CS A', '4º ESO B', etc.")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(message = "La institución no puede ser nula")
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
