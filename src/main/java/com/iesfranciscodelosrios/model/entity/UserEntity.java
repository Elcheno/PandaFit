package com.iesfranciscodelosrios.model.entity;


import com.iesfranciscodelosrios.model.type.RoleType;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false, unique = true)
    private Institution institution;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleType> role;

    @OneToMany(mappedBy = "userOwner")
    private Set<Input> inputList;

    @OneToMany(mappedBy = "userOwner")
    private Set<Output> outputList;

    @OneToMany(mappedBy = "userOwner")
    private Set<Form> formList;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", institution=" + (institution != null ? institution.getName() : null) +
                '}';
    }
}
