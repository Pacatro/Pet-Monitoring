package ie.mtu.petmonitoring.service;

import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseholdServiceTest {

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdService householdService;

    private Household testHousehold;

    @BeforeEach
    void setUp() {
        testHousehold = new Household();
        testHousehold.setEircode("D01AB12");
        testHousehold.setNumberOfOccupants(2);
        testHousehold.setMaxNumberOfOccupants(4);
        testHousehold.setOwnerOccupied(true);
    }

    @Test
    void getAllHouseholds_ShouldReturnListOfHouseholds() {
        when(householdRepository.findAll()).thenReturn(Arrays.asList(testHousehold));

        List<Household> households = householdService.getAllHouseholds();

        assertFalse(households.isEmpty());
        assertEquals(1, households.size());
        assertEquals("D01AB12", households.get(0).getEircode());
    }

    @Test
    void getHousehold_WhenHouseholdExists_ShouldReturnHousehold() {
        when(householdRepository.findById("D01AB12")).thenReturn(Optional.of(testHousehold));

        Household household = householdService.getHousehold("D01AB12");

        assertNotNull(household);
        assertEquals("D01AB12", household.getEircode());
    }

    @Test
    void getHousehold_WhenHouseholdDoesNotExist_ShouldThrowException() {
        when(householdRepository.findById("D01AB12")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> householdService.getHousehold("D01AB12"));
    }

    @Test
    void createHousehold_ShouldReturnCreatedHousehold() {
        when(householdRepository.save(any(Household.class))).thenReturn(testHousehold);

        Household createdHousehold = householdService.createHousehold(testHousehold);

        assertNotNull(createdHousehold);
        assertEquals("D01AB12", createdHousehold.getEircode());
    }

    @Test
    void getHouseholdsWithNoPets_ShouldReturnHouseholdsWithoutPets() {
        when(householdRepository.findHouseholdsWithNoPets()).thenReturn(Arrays.asList(testHousehold));

        List<Household> households = householdService.getHouseholdsWithNoPets();

        assertFalse(households.isEmpty());
        assertEquals(1, households.size());
    }
}