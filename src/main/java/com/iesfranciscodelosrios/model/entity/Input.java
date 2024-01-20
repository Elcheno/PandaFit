package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "input")
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "validator")
    @NotBlank(message = "El validador no puede estar vacío")
    private String validator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id", nullable = false)
    @NotNull(message = "El propietario del usuario no puede ser nulo")
    private UserEntity userOwner;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
