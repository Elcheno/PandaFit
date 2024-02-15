package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "form")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "El nombre del formulario no puede estar en blanco")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userEntity_id")
    @NotNull(message = "El propietario del formulario no puede ser nulo")
    private UserEntity userOwner;

    @OneToMany(mappedBy = "form")
    private Set<FormAct> formActList;

    @OneToMany
    @JoinColumn(name = "output")
    private Set<Output> outputList;

    @OneToMany
    @JoinColumn(name = "input")
    private Set<Input> inputList;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userOwner=" + (userOwner != null ? userOwner.getEmail() : null) +
                '}';
    }

}
