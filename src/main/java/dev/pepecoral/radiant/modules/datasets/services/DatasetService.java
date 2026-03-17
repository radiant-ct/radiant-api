package dev.pepecoral.radiant.modules.datasets.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import dev.pepecoral.radiant.modules.common.exceptions.ResourceNotFoundException;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.repositories.DatasetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository datasetRepository;

    public Dataset create(@Valid Dataset dataset) {

        if (dataset.getId() != null) {
            throw new IllegalArgumentException("Id must be null when creating a new Dataset");
        }
        return datasetRepository.save(dataset);
    }

    public Dataset findById(@NotNull UUID uuid) {
        return datasetRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("There is no dataset with id: " + uuid));
    }

    public List<Dataset> findAll() {
        return datasetRepository.findAll();
    }

}
