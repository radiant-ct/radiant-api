package dev.pepecoral.radiant.modules.datasets.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    void imageRequiresDataset() {
        Image image = ImageTestBuilder.builder().dataset(null).build().entity();

        assertThrows(Exception.class, () -> imageRepository.saveAndFlush(image));
    }
}
