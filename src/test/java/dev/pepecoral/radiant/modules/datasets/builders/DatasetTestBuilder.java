package dev.pepecoral.radiant.modules.datasets.builders;

import java.util.UUID;

import dev.pepecoral.radiant.modules.common.builders.SampleDataGenerator;
import dev.pepecoral.radiant.modules.common.builders.TestBuilder;
import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import lombok.Builder;

@Builder
public class DatasetTestBuilder implements TestBuilder<Dataset> {

    @Builder.Default
    UUID id = null;

    @Builder.Default
    String name = SampleDataGenerator.generateName("dataset");

    @Builder.Default
    String description = SampleDataGenerator.generateText(20);

    @Builder.Default
    String credits = SampleDataGenerator.generateText(20);

    public Dataset entity() {

        Dataset dataset = new Dataset();
        dataset.setId(id);
        dataset.setName(name);
        dataset.setDescription(description);
        dataset.setCredits(credits);

        return dataset;
    }

    public Dataset persist(TestPersistenceContext ctx) {
        Dataset dataset = entity();
        Dataset savedDataset = ctx.datasetRepository.save(dataset);
        return savedDataset;
    }
}
