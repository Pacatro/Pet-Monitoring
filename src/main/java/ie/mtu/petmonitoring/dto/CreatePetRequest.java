package ie.mtu.petmonitoring.dto;

import jakarta.validation.constraints.*;

public record CreatePetRequest(
    @NotBlank(message = "Pet name cannot be blank")
    @Size(min = 2, max = 50, message = "Pet name must be between 2 and 50 characters")
    String name,

    @NotBlank(message = "Animal type cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Animal type must contain only letters and spaces")
    String animalType,

    @Size(max = 50, message = "Breed cannot exceed 50 characters")
    String breed,

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 30, message = "Age cannot exceed 30 years")
    Integer age,

    @NotBlank(message = "Household eircode is required")
    @Pattern(regexp = "^[A-Z0-9]{7}$", message = "Eircode must be 7 characters long and contain only uppercase letters and numbers")
    String householdEircode
) {}