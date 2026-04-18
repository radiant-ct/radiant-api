package dev.pepecoral.radiant.modules.datasets.dtos;

import java.util.UUID;

import dev.pepecoral.radiant.modules.datasets.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDTO {

    private UUID id;

    private UUID datasetId;

    public ImageResponseDTO(Image image) {
        this.id = image.getId();
        this.datasetId = image.getDataset().getId();
    }
}
