package dev.pepecoral.radiant.modules.datasets.dtos;

import dev.pepecoral.radiant.modules.datasets.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageCreationDTO {

    private Integer dummyInteger; // TODO: Remove this and add useful information, this is needed for the lombok
                                  // constructors

    public Image toImage() {
        return new Image();
    }

    public ImageCreationDTO(Image image) {
        this.dummyInteger = null;
    }
}
