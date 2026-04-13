package dev.pepecoral.radiant.modules.datasets.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

@DataJpaTest
public class DatasetRepositoryTest {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @Transactional
    void deletingDataset_shouldRemoveImages() {
        Dataset dataset = DatasetTestBuilder.builder().build().entity();
        Dataset savedDataset = datasetRepository.save(dataset);
        datasetRepository.flush();

        Image img1 = ImageTestBuilder.builder().dataset(savedDataset).build().entity();
        imageRepository.save(img1);

        Image img2 = ImageTestBuilder.builder().dataset(savedDataset).build().entity();
        imageRepository.save(img2);
        imageRepository.flush();

        List<Image> imagesBefore = imageRepository.findAll();
        assertEquals(2, imagesBefore.size());

        datasetRepository.delete(dataset);
        datasetRepository.flush();

        List<Image> imagesAfter = imageRepository.findAll();
        assertTrue(imagesAfter.isEmpty(), "All images should be deleted with the dataset");
    }

}