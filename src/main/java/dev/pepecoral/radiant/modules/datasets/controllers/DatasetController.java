package dev.pepecoral.radiant.modules.datasets.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pepecoral.radiant.modules.datasets.dtos.DatasetResponseDTO;
import dev.pepecoral.radiant.modules.datasets.dtos.ImageResponseDTO;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.services.DatasetService;
import dev.pepecoral.radiant.modules.datasets.services.ImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/datasets")
@Validated
@RequiredArgsConstructor
public class DatasetController {

    private final DatasetService datasetService;
    private final ImageService imageService;

    @GetMapping("/{datasetId}")
    public ResponseEntity<DatasetResponseDTO> getDatasetById(@PathVariable UUID datasetId) {

        Dataset dataset = datasetService.findById(datasetId);
        DatasetResponseDTO datasetResponseDTO = new DatasetResponseDTO(dataset);
        return ResponseEntity.ok(datasetResponseDTO);
    }

    @GetMapping("/{datasetId}/images")
    public ResponseEntity<List<ImageResponseDTO>> getImagesByDatasetId(@PathVariable UUID datasetId) {

        Dataset dataset = datasetService.findById(datasetId);
        List<Image> images = imageService.findByDataset(dataset);
        List<ImageResponseDTO> imageDtos = images.stream().map(ImageResponseDTO::new).toList();
        return ResponseEntity.ok(imageDtos);
    }

}
