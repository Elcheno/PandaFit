package com.iesfranciscodelosrios.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String name;

    @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserEntity> userList;

    @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SchoolYear> schoolYearList;

    /*@Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }*/
}
