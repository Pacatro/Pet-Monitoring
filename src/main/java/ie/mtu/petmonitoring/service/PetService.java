package ie.mtu.petmonitoring.service;

import ie.mtu.petmonitoring.dto.CreatePetRequest;
import ie.mtu.petmonitoring.dto.PetStatistics;
import ie.mtu.petmonitoring.model.Pet;
import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.repository.PetRepository;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final HouseholdRepository householdRepository;

    public PetService(PetRepository petRepository, HouseholdRepository householdRepository) {
        this.petRepository = petRepository;
        this.householdRepository = householdRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public Pet createPet(CreatePetRequest request) {
        Household household = householdRepository.findById(request.getHouseholdEircode())
                .orElseThrow(() -> new RuntimeException("Household not found"));

        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setAnimalType(request.getAnimalType());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setHousehold(household);

        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    public Pet updatePetName(Long id, String newName) {
        Pet pet = getPet(id);
        pet.setName(newName);
        return petRepository.save(pet);
    }

    public PetStatistics getPetStatistics() {
        PetStatistics stats = new PetStatistics();
        stats.setAverageAge(petRepository.getAverageAge());
        stats.setTotalCount(petRepository.count());
        return stats;
    }
}