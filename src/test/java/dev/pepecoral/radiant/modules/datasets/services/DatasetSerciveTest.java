package dev.pepecoral.radiant.modules.datasets.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

@SpringBootTest
public class DatasetSerciveTest {

    @Autowired
    DatasetService datasetService;

    @Test
    public void shouldCreateDataset_whenValidData() {
        Dataset dataset = DatasetTestBuilder.builder().build().entity();
        Dataset createdDataset = datasetService.create(dataset);
        assertNotNull(createdDataset);
        assertNotNull(createdDataset.getId());
    }

}
