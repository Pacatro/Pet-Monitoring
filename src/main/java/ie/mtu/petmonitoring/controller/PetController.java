package ie.mtu.petmonitoring.controller;

import ie.mtu.petmonitoring.dto.CreatePetRequest;
import ie.mtu.petmonitoring.dto.PetStatistics;
import ie.mtu.petmonitoring.model.Pet;
import ie.mtu.petmonitoring.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable Long id) {
        return petService.getPet(id);
    }

    @PostMapping
    public Pet createPet(@RequestBody CreatePetRequest request) {
        return petService.createPet(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/name")
    public Pet updatePetName(@PathVariable Long id, @RequestBody String newName) {
        return petService.updatePetName(id, newName);
    }

    @GetMapping("/statistics")
    public PetStatistics getPetStatistics() {
        return petService.getPetStatistics();
    }
}