package dev.pepecoral.radiant.modules.datasets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.repositories.DatasetRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;

    public Dataset create(@Valid Dataset dataset) {
        return datasetRepository.save(dataset);
    }
}
