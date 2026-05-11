package dev.pepecoral.radiant.modules.datasets.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import dev.pepecoral.radiant.modules.common.services.QueueService;
import dev.pepecoral.radiant.modules.datasets.dtos.*;
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

    private final QueueService queueService;
    private final DatasetService datasetService;
    private final ImageService imageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetResponseDTO> getAllDatasets() {
        return datasetService.findAll()
                .stream()
                .map(DatasetResponseDTO::new)
                .toList();
    }

    @GetMapping(value = "/{datasetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DatasetResponseDTO getDatasetById(@PathVariable UUID datasetId) {
        Dataset dataset = datasetService.findById(datasetId);
        return new DatasetResponseDTO(dataset);
    }

    @GetMapping(value = "/{datasetId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageResponseDTO> getImagesByDatasetId(@PathVariable UUID datasetId) {
        Dataset dataset = datasetService.findById(datasetId);
        return imageService.findByDataset(dataset)
                .stream()
                .map(ImageResponseDTO::new)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DatasetResponseDTO createDataset(
            @RequestPart("data") DatasetCreationDTO datasetCreationDTO,
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        Dataset dataset = queueService.queueDataset(datasetCreationDTO.toDataset(), file);
        return new DatasetResponseDTO(dataset);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{datasetId}/images", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageResponseDTO createImage(
            @RequestBody ImageCreationDTO imageCreationDTO,
            @PathVariable UUID datasetId) {

        Dataset dataset = datasetService.findById(datasetId);
        Image image = imageService.create(imageCreationDTO.toImage(), dataset);

        return new ImageResponseDTO(image);
    }
}