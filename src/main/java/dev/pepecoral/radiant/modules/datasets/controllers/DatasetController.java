package dev.pepecoral.radiant.modules.datasets.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.pepecoral.radiant.modules.common.services.QueueService;
import dev.pepecoral.radiant.modules.datasets.dtos.DatasetCreationDTO;
import dev.pepecoral.radiant.modules.datasets.dtos.DatasetResponseDTO;
import dev.pepecoral.radiant.modules.datasets.dtos.ImageCreationDTO;
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

    private final QueueService queueService;
    private final DatasetService datasetService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<DatasetResponseDTO>> getAllDatasets() {
        List<Dataset> datasets = datasetService.findAll();
        List<DatasetResponseDTO> datasetResponseDTOs = datasets.stream().map(DatasetResponseDTO::new).toList();
        return ResponseEntity.ok(datasetResponseDTOs);
    }

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DatasetResponseDTO> createDataset(
            @RequestPart("data") DatasetCreationDTO datasetCreationDTO,
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        Dataset dataset = queueService.queueDataset(datasetCreationDTO.toDataset(), file);

        return new ResponseEntity<>(new DatasetResponseDTO(dataset), HttpStatus.CREATED);
    }

    @PostMapping("{datasetId}/images")
    public ResponseEntity<ImageResponseDTO> createImage(@RequestBody ImageCreationDTO imageCreationDTO,
            @PathVariable UUID datasetId) {

        Dataset dataset = datasetService.findById(datasetId);
        Image image = imageService.create(imageCreationDTO.toImage(), dataset);
        ImageResponseDTO imageResponseDTO = new ImageResponseDTO(image);
        return new ResponseEntity<>(imageResponseDTO, HttpStatus.CREATED);

    }

}
