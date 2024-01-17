package com.iesfranciscodelosrios.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@EqualsAndHashCode
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date", unique = true)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "formaAct_id", nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    private FormAct formAct;

    @Column(name = "uuid", unique = true)
    private String uuid;

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
