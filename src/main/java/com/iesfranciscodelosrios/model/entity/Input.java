package com.iesfranciscodelosrios.model.entity;

import com.iesfranciscodelosrios.model.type.TypeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
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

    @NotNull(message = "El tipo del valor no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private TypeType type;

    @Column(name = "decimal")
    private Boolean decimal;

    @Column(name = "decimals")
    private Number decimals;

    @Column(name = "unit")
    private String unit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id", nullable = false)
    @Cascade(CascadeType.PERSIST)
    @NotNull(message = "El propietario de un Input no puede ser nulo")
    private UserEntity userOwner;

    @ManyToMany(
            cascade = { jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.PERSIST },
            fetch = FetchType.EAGER,
            mappedBy = "inputs")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Output> outputs;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
