package ie.mtu.petmonitoring.controller;

import ie.mtu.petmonitoring.dto.CreatePetRequest;
import ie.mtu.petmonitoring.model.Household;
import ie.mtu.petmonitoring.repository.HouseholdRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HouseholdRepository householdRepository;

    @BeforeEach
    void setUp() {
        householdRepository.deleteAll();

        Household household = new Household();
        household.setEircode("D01AB12");
        household.setNumberOfOccupants(2);
        household.setMaxNumberOfOccupants(4);
        household.setOwnerOccupied(true);
        householdRepository.save(household);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createPet_ShouldCreateNewPet() throws Exception {
        CreatePetRequest request = new CreatePetRequest(
                "Max",
                "Dog",
                "Labrador",
                5,
                "D01AB12"
        );

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.animalType").value("Dog"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllPets_ShouldReturnPets() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getPetStatistics_ShouldReturnStatistics() throws Exception {
        mockMvc.perform(get("/api/pets/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCount").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createPet_ShouldFailWithInvalidData() throws Exception {
        CreatePetRequest request = new CreatePetRequest(
                "",  // Invalid name
                "Dog",
                "Labrador",
                -1,  // Invalid age
                "D01AB12"
        );

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}