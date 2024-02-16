package com.iesfranciscodelosrios.model.entity;


import com.iesfranciscodelosrios.model.type.RoleType;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "El formato del correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Column(name = "email", unique = true)
    private String email;


    @NotBlank(message = "La contraseña no puede estar en blanco")
//    @Pattern(
//            regexp = "^[a-zA-ZÁáÀàÉéÈèÍíÌìÓóÒòÚúÙùÑñüÜ0-9!@#\\$%\\^&\\*\\?_~\\/]{8,20}$",
//            message = "La contraseña no es lo suficientemente segura"
//    ) // 8-20 caracteres alfanúmericos
    @Column(name = "password")
    private String password;

    @Column(name = "uuid")
    private String uuid;

    @NotNull(message = "La institución no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;


    //    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @NotNull(message = "Los roles no pueden ser nulos")
    @Size(min = 1, message = "Debe tener al menos un rol")
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role;

    @OneToMany(mappedBy = "userOwner", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Input> inputList;

    @OneToMany(mappedBy = "userOwner", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Output> outputList;

    @OneToMany(mappedBy = "userOwner", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Form> formList;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Usar solo la id para hashCode
    }

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
