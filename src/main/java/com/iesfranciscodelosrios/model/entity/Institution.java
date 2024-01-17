package com.iesfranciscodelosrios.model.entity;


import jakarta.persistence.*;
import lombok.*;

 import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
    private Set<UserEntity> userList;

    @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
    private Set<SchoolYear> schoolYearList;

    /*@Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }*/
}
