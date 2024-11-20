package ie.mtu.petmonitoring.service;

import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseholdService {
    private final HouseholdRepository householdRepository;

    public HouseholdService(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    public Household getHousehold(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
    }

    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    public void deleteHousehold(String eircode) {
        householdRepository.deleteById(eircode);
    }

    public List<Household> getHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }
}