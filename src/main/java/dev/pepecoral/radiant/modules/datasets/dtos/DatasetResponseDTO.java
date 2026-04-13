package dev.pepecoral.radiant.modules.datasets.dtos;

import java.util.UUID;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasetResponseDTO {

    private UUID id;

    @Size(max = 255)
    @NotBlank
    private String name;

    @Size(max = 5000)
    @NotBlank
    private String description;

    @Size(max = 5000)
    @NotBlank
    private String credits;

    public DatasetResponseDTO(Dataset dataset) {
        this.id = dataset.getId();
        this.name = dataset.getName();
        this.description = dataset.getDescription();
        this.credits = dataset.getCredits();
    }

}