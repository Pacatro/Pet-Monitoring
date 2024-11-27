package ie.mtu.petmonitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Household {
    @Id
    private String eircode;
    private Integer numberOfOccupants;
    private Integer maxNumberOfOccupants;
    private Boolean ownerOccupied;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("household")
    private List<Pet> pets;
}

