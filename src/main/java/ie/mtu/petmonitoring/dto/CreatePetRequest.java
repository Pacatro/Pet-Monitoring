package ie.mtu.petmonitoring.dto;

import lombok.Data;

@Data
public class CreatePetRequest {
    private String name;
    private String animalType;
    private String breed;
    private Integer age;
    private String householdEircode;  // Instead of the whole Household object, we just need the eircode
}