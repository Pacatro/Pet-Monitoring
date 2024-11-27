package ie.mtu.petmonitoring.controller;

import ie.mtu.petmonitoring.dto.CreatePetRequest;
import ie.mtu.petmonitoring.dto.PetStatistics;
import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.model.Pet;
import ie.mtu.petmonitoring.service.HouseholdService;
import ie.mtu.petmonitoring.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLController {
    private final PetService petService;
    private final HouseholdService householdService;

    public GraphQLController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    // Pet Queries
    @QueryMapping
    public Pet getPet(@Argument Long id) {
        return petService.getPet(id);
    }

    @QueryMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public PetStatistics getPetStatistics() {
        return petService.getPetStatistics();
    }

    // Household Queries
    @QueryMapping
    public Household getHousehold(@Argument String eircode) {
        return householdService.getHousehold(eircode);
    }

    @QueryMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.getHouseholdsWithNoPets();
    }

    // Pet Mutations
    @MutationMapping
    public Pet createPet(@Argument("Pet") CreatePetRequest petRequest) {
        return petService.createPet(petRequest);
    }

    @MutationMapping
    public Pet updatePetName(@Argument Long id, @Argument String newName) {
        return petService.updatePetName(id, newName);
    }

    @MutationMapping
    public boolean deletePet(@Argument Long id) {
        try {
            petService.deletePet(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Household Mutations
    @MutationMapping
    public Household createHousehold(@Argument("household") Household household) {
        return householdService.createHousehold(household);
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        try {
            householdService.deleteHousehold(eircode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}