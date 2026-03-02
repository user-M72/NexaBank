package BankApp.SpringBank.model;

import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseDomain<UUID> {

    @Column(name = "firstName", nullable = false, length = 30)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles;
}
