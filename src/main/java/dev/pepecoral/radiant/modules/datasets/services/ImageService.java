package dev.pepecoral.radiant.modules.datasets.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import dev.pepecoral.radiant.modules.common.exceptions.ResourceNotFoundException;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.exceptions.DatasetSetInImageCreationException;
import dev.pepecoral.radiant.modules.datasets.repositories.ImageRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final DatasetService datasetService;

    public Image create(Image image, @NotNull Dataset dataset) {

        if (image.getDataset() != null) {
            throw new DatasetSetInImageCreationException();
        }

        Dataset gotDataset = datasetService.findById(dataset.getId());
        image.setDataset(gotDataset);
        return imageRepository.save(image);
    }

    public Image findById(@NotNull UUID imageId) {

        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<Image> findByDataset(Dataset dataset) {
        return imageRepository.findAllByDataset(dataset);
    }
}
