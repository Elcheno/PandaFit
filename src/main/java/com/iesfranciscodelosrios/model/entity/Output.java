package com.iesfranciscodelosrios.model.entity;

import com.iesfranciscodelosrios.utils.HashMapConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map;

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

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotBlank(message = "La fórmula no puede estar en blanco")
    @Column(name = "formula")
    private String formula;

    @NotNull(message = "El usuario propietario de Output no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    @Cascade(CascadeType.PERSIST)
    private UserEntity userOwner;

    @Convert(converter = HashMapConverter.class)
    private Set<Object> umbralList;

    @NotBlank(message = "Las unidades no puede estar en blanco")
    @Column(name = "unit")
    private String unit;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}