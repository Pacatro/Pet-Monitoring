package ie.mtu.petmonitoring.service;

import ie.mtu.petmonitoring.dto.CreatePetRequest;
import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.model.Pet;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
import ie.mtu.petmonitoring.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private PetService petService;

    private Pet testPet;
    private Household testHousehold;
    private CreatePetRequest createPetRequest;

    @BeforeEach
    void setUp() {
        testHousehold = new Household();
        testHousehold.setEircode("D01AB12");
        testHousehold.setNumberOfOccupants(2);
        testHousehold.setMaxNumberOfOccupants(4);
        testHousehold.setOwnerOccupied(true);

        testPet = new Pet();
        testPet.setId(1L);
        testPet.setName("Max");
        testPet.setAnimalType("Dog");
        testPet.setBreed("Labrador");
        testPet.setAge(5);
        testPet.setHousehold(testHousehold);

        createPetRequest = new CreatePetRequest();
        createPetRequest.setName("Max");
        createPetRequest.setAnimalType("Dog");
        createPetRequest.setBreed("Labrador");
        createPetRequest.setAge(5);
        createPetRequest.setHouseholdEircode("D01AB12");
    }

    @Test
    void getAllPets_ShouldReturnListOfPets() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(testPet));

        List<Pet> pets = petService.getAllPets();

        assertFalse(pets.isEmpty());
        assertEquals(1, pets.size());
        assertEquals("Max", pets.get(0).getName());
    }

    @Test
    void getPet_WhenPetExists_ShouldReturnPet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));

        Pet pet = petService.getPet(1L);

        assertNotNull(pet);
        assertEquals("Max", pet.getName());
    }

    @Test
    void getPet_WhenPetDoesNotExist_ShouldThrowException() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.getPet(1L));
    }

    @Test
    void createPet_WhenHouseholdExists_ShouldCreatePet() {
        when(householdRepository.findById("D01AB12")).thenReturn(Optional.of(testHousehold));
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        Pet createdPet = petService.createPet(createPetRequest);

        assertNotNull(createdPet);
        assertEquals("Max", createdPet.getName());
        assertEquals("D01AB12", createdPet.getHousehold().getEircode());
    }

    @Test
    void createPet_WhenHouseholdDoesNotExist_ShouldThrowException() {
        when(householdRepository.findById("D01AB12")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.createPet(createPetRequest));
    }

    @Test
    void updatePetName_WhenPetExists_ShouldUpdateName() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        Pet updatedPet = petService.updatePetName(1L, "Rex");

        assertEquals("Rex", updatedPet.getName());
    }
}
