package ie.mtu.petmonitoring.controller;

import ie.mtu.petmonitoring.dto.CreateHouseholdRequest;
import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {
    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/{eircode}")
    public Household getHousehold(@PathVariable String eircode) {
        return householdService.getHousehold(eircode);
    }

    @PostMapping
    public Household createHousehold(@Valid @RequestBody CreateHouseholdRequest request) {
        Household household = new Household();
        household.setEircode(request.eircode());
        household.setNumberOfOccupants(request.numberOfOccupants());
        household.setMaxNumberOfOccupants(request.maxNumberOfOccupants());
        household.setOwnerOccupied(request.ownerOccupied());

        return householdService.createHousehold(household);
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/no-pets")
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.getHouseholdsWithNoPets();
    }
}
