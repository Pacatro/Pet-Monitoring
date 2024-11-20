package ie.mtu.petmonitoring.repository;

import ie.mtu.petmonitoring.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, String> {
    @Query("SELECT h FROM Household h LEFT JOIN h.pets p WHERE p.id IS NULL")
    List<Household> findHouseholdsWithNoPets();
}
