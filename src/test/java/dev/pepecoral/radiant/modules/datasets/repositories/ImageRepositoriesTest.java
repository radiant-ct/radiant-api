package dev.pepecoral.radiant.modules.datasets.repositories;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import dev.pepecoral.radiant.modules.common.builders.TestPersistenceContext;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

@DataJpaTest
public class ImageRepositoriesTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TestPersistenceContext testPersistenceContext;

    @Test
    void imageRequiresDataset() {
        Image image = ImageTestBuilder.builder().dataset(null).build().entity();

        assertThrows(Exception.class, () -> imageRepository.saveAndFlush(image));
    }
}
