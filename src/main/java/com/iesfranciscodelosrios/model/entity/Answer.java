package com.iesfranciscodelosrios.model.entity;

import com.iesfranciscodelosrios.utils.HashMapConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "answer", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"formAct", "uuid"})
})
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "formAct", nullable = false)
    @EqualsAndHashCode.Exclude
    private FormAct formAct;

    //userId autogenerada por nosotros ejemplo: agutcru403
    @Column(name = "uuid")
    @NotBlank(message = "El campo UUID no puede estar en blanco")
    private String uuid;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "VARCHAR")
    private Set<Object> response;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "VARCHAR")
    private Set<Object> outputs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) &&
                Objects.equals(date, answer.date) &&
                Objects.equals(uuid, answer.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, uuid);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", date=" + date +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}