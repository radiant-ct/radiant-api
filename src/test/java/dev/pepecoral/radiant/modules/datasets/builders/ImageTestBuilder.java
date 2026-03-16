package dev.pepecoral.radiant.modules.datasets.builders;

import java.util.UUID;

import dev.pepecoral.radiant.modules.common.builders.TestBuilder;
import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import lombok.Builder;

@Builder
public class ImageTestBuilder implements TestBuilder<Image> {

    @Builder.Default
    UUID id = null;

    @Builder.Default
    Dataset dataset = DatasetTestBuilder.builder().build().entity();

    @Override
    public Image entity() {
        Image image = new Image();
        image.setId(id);
        image.setDataset(dataset);

        return image;
    }

    @Override
    public Image persist(TestPersistenceContext ctx) {
        Image image = entity();

        Dataset savedDataset = ctx.datasetRepository.save(image.getDataset());
        image.setDataset(savedDataset);

        Image savedImage = ctx.imageRepository.save(image);

        return savedImage;
    }
}
