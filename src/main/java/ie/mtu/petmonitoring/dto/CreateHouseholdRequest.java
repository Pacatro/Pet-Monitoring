package ie.mtu.petmonitoring.dto;

import jakarta.validation.constraints.*;

public record CreateHouseholdRequest(
    @NotBlank(message = "Eircode cannot be blank")
    @Pattern(regexp = "^[A-Z0-9]{7}$", message = "Eircode must be 7 characters long and contain only uppercase letters and numbers")
    String eircode,

    @NotNull(message = "Number of occupants is required")
    @Min(value = 0, message = "Number of occupants cannot be negative")
    @Max(value = 20, message = "Number of occupants cannot exceed 20")
    Integer numberOfOccupants,

    @NotNull(message = "Maximum number of occupants is required")
    @Min(value = 1, message = "Maximum number of occupants must be at least 1")
    @Max(value = 20, message = "Maximum number of occupants cannot exceed 20")
    Integer maxNumberOfOccupants,

    @NotNull(message = "Owner occupied status is required")
    Boolean ownerOccupied
) {}
