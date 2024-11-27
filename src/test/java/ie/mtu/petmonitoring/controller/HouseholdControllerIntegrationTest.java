package ie.mtu.petmonitoring.controller;

import ie.mtu.petmonitoring.dto.CreateHouseholdRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HouseholdControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createHousehold_ShouldCreateNewHousehold() throws Exception {
        CreateHouseholdRequest householdRequest = new CreateHouseholdRequest(
            "D02CD34",
            3,
            5,
            false
        );

        mockMvc.perform(post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(householdRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("D02CD34"));
    }

    @Test
    void createHousehold_ShouldFailWithInvalidOccupants() throws Exception {
        CreateHouseholdRequest householdRequest = new CreateHouseholdRequest(
                "D02CD34",
                -1, // Invalid parameter
                5,
                false
        );

        mockMvc.perform(post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(householdRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllHouseholds_ShouldReturnHouseholds() throws Exception {
        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getHouseholdsWithNoPets_ShouldReturnHouseholdsWithoutPets() throws Exception {
        mockMvc.perform(get("/api/households/no-pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
