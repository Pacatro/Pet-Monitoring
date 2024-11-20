package ie.mtu.petmonitoring.config;

import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.model.Pet;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
import ie.mtu.petmonitoring.repository.PetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(HouseholdRepository householdRepository, PetRepository petRepository) {
        return args -> {
            // Create households
            Household h1 = new Household();
            h1.setEircode("D01AB12");
            h1.setNumberOfOccupants(2);
            h1.setMaxNumberOfOccupants(4);
            h1.setOwnerOccupied(true);

            Household h2 = new Household();
            h2.setEircode("D02CD34");
            h2.setNumberOfOccupants(3);
            h2.setMaxNumberOfOccupants(5);
            h2.setOwnerOccupied(false);

            householdRepository.saveAll(Arrays.asList(h1, h2));

            // Create pets
            Pet p1 = new Pet();
            p1.setName("Max");
            p1.setAnimalType("Dog");
            p1.setBreed("Labrador");
            p1.setAge(5);
            p1.setHousehold(h1);

            Pet p2 = new Pet();
            p2.setName("Luna");
            p2.setAnimalType("Cat");
            p2.setBreed("Persian");
            p2.setAge(3);
            p2.setHousehold(h1);

            petRepository.saveAll(Arrays.asList(p1, p2));
        };
    }
}