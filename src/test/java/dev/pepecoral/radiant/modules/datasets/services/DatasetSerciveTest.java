package dev.pepecoral.radiant.modules.datasets.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;
import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class DatasetSerciveTest {

    @Autowired
    DatasetService datasetService;

    @Autowired
    TestPersistenceContext testPersistenceContext;

    @Test
    public void shouldCreateDataset_whenValidData() {
        Dataset dataset = DatasetTestBuilder.builder().build().entity();
        Dataset createdDataset = datasetService.create(dataset);
        assertNotNull(createdDataset);
        assertNotNull(createdDataset.getId());
    }

    @Test
    public void shouldThrow_whenCreateDatasetNameNull() {
        Dataset dataset = DatasetTestBuilder.builder().name(null).build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetNameEmpty() {
        Dataset dataset = DatasetTestBuilder.builder().name("").build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetDescriptionNull() {
        Dataset dataset = DatasetTestBuilder.builder().description(null).build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetDescriptionEmpty() {
        Dataset dataset = DatasetTestBuilder.builder().description("").build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetCreditsNull() {
        Dataset dataset = DatasetTestBuilder.builder().credits(null).build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetCreditsEmpty() {
        Dataset dataset = DatasetTestBuilder.builder().credits("").build().entity();
        assertThrows(ConstraintViolationException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldThrow_whenCreateDatasetWithIdNotNull() {
        Dataset dataset = DatasetTestBuilder.builder().id(UUID.randomUUID()).build().entity();
        assertThrows(IllegalArgumentException.class, () -> datasetService.create(dataset));
    }

    @Test
    public void shouldGetDatasetById() {
        Dataset dataset = DatasetTestBuilder.builder().build().persist(testPersistenceContext);
        Dataset gotDataset = datasetService.getById(dataset.getId());

        assertNotNull(gotDataset);
        assertEquals(dataset, gotDataset);
        assertEquals(dataset.getName(), gotDataset.getName());
        assertEquals(dataset.getDescription(), gotDataset.getDescription());
    }

}
