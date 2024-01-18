package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
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
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "validator")
    private String validator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id", nullable = false)
    private UserEntity userOwner;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
