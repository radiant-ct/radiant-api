package dev.pepecoral.radiant.modules.common.builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.pepecoral.radiant.modules.datasets.repositories.DatasetRepository;
import dev.pepecoral.radiant.modules.datasets.repositories.ImageRepository;

@Component
public class TestPersistenceContext {

    @Autowired
    public DatasetRepository datasetRepository;

    @Autowired
    public ImageRepository imageRepository;
}
