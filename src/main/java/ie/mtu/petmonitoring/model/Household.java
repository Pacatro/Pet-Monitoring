package ie.mtu.petmonitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Household {
    @Id
    private String eircode;
    private Integer numberOfOccupants;
    private Integer maxNumberOfOccupants;
    private Boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("household")
    private List<Pet> pets;
}

