package dev.pepecoral.radiant.modules.datasets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.repositories.DatasetRepository;

@Service
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;

    public Dataset create(Dataset dataset) {
        return datasetRepository.save(dataset);
    }
}
