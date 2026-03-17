package dev.pepecoral.radiant.modules.datasets.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;
import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

@SpringBootTest
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Autowired
    TestPersistenceContext testPersistenceContext;

    @Test
    public void shouldCreateImage() {
        Dataset dataset = DatasetTestBuilder.builder().build().persist(testPersistenceContext);
        Image image = ImageTestBuilder.builder().build().entity();
        Image savedImage = imageService.create(image, dataset);

        assertNotNull(savedImage);
        assertNotNull(savedImage.getId());
    }
}
