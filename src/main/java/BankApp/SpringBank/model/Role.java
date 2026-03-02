package BankApp.SpringBank.model;

import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseDomain<UUID> {

    @Column(name = "name", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private String name;

    @Column(name = "description", nullable = false, length = 100)
    private String description;
}
