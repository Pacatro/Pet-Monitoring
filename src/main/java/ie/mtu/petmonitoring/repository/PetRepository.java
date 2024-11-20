package ie.mtu.petmonitoring.repository;

import ie.mtu.petmonitoring.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double getAverageAge();
}