package ie.mtu.petmonitoring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();

    // Constructors
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }
}
