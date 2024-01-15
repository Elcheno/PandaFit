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

    @OneToMany(mappedBy = "institution")
    private Set<UserEntity> userList;

    @OneToMany(mappedBy = "institution")
    private Set<SchoolYear> schoolYearList;
}
