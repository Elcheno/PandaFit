package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Pattern(regexp = "^[A-Za-z0-9]{2,25}$", message = " Debe haber entre 2 y 25 dígitos")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(message = "La institución no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Institution institution;

    @OneToMany(mappedBy = "schoolYear", cascade = {CascadeType.ALL})
    @EqualsAndHashCode.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
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
