package dev.pepecoral.radiant.modules.datasets.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private String sex;

    private Integer ageYears;

    private String manufacturer;

    private String manufacturerModel;

    private boolean hasSegmentation;

    private Integer nSlices;

    public ImageResponseDTO(Image image) {
        this.id = image.getId();
        this.datasetId = image.getDataset().getId();
        this.sex = image.getSex();
        this.ageYears = image.getAgeYears();
        this.manufacturer = image.getManufacturer();
        this.manufacturerModel = image.getManufacturerModel();
        this.hasSegmentation = image.isHasSegmentation();
        this.nSlices = image.getNSlices();
    }

    @JsonProperty("nSlices")
    public Integer getnSlices() {
        return nSlices;
    }
}
