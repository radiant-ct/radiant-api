package dev.pepecoral.radiant.modules.datasets.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;

import dev.pepecoral.radiant.modules.common.exceptions.ResourceNotFoundException;
import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.exceptions.DatasetSetInImageCreationException;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Autowired
    TestPersistenceContext testPersistenceContext;

    @Test
    public void shouldCreateImage() {
        Dataset dataset = DatasetTestBuilder.builder().build().persist(testPersistenceContext);
        Image image = ImageTestBuilder.builder().dataset(null).build().entity();
        Image savedImage = imageService.create(image, dataset);

        assertNotNull(savedImage);
        assertNotNull(savedImage.getId());
        assertEquals(savedImage.getDataset(), dataset);
    }

    @Test
    public void shouldThrow_whenDatasetNotPersisted() {
        Dataset dataset = DatasetTestBuilder.builder().build().entity();
        Image image = ImageTestBuilder.builder().dataset(null).build().entity();
        assertThrows(ConstraintViolationException.class, () -> imageService.create(image, dataset));
    }

    @Test
    public void shouldThrow_whenDatasetNull() {
        Image image = ImageTestBuilder.builder().dataset(null).build().entity();
        assertThrows(ConstraintViolationException.class, () -> imageService.create(image, null));
    }

    @Test
    public void shouldThrow_whenDatasetSet() {
        Dataset dataset = DatasetTestBuilder.builder().build().entity();
        Image image = ImageTestBuilder.builder().build().entity();
        assertThrows(DatasetSetInImageCreationException.class, () -> imageService.create(image, dataset));
    }

    @Test
    public void shouldFindImageById() {
        UUID imageId = ImageTestBuilder.builder().build().persist(testPersistenceContext).getId();
        Image image = imageService.findById(imageId);
        assertNotNull(image);
        assertEquals(imageId, image.getId());

    }

    @Test
    public void shouldThrow_whenIdDoesntExist() {
        assertThrows(ResourceNotFoundException.class, () -> imageService.findById(UUID.randomUUID()));
    }

    @Test
    public void shouldThrow_whenIdisNull() {
        assertThrows(ConstraintViolationException.class, () -> imageService.findById(null));
    }

}
