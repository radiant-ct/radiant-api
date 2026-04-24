package dev.pepecoral.radiant.modules.datasets.dtos;

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
public class DatasetCreationDTO {

    @Size(max = 255)
    @NotBlank
    private String name;

    @Size(max = 5000)
    @NotBlank
    private String description;

    @Size(max = 5000)
    @NotBlank
    private String credits;

    public Dataset toDataset() {
        Dataset dataset = new Dataset();
        dataset.setName(name);
        dataset.setDescription(description);
        dataset.setCredits(credits);
        return dataset;
    }
}
