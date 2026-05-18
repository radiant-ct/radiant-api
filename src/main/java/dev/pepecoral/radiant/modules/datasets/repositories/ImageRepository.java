package dev.pepecoral.radiant.modules.datasets.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {

    List<Image> findAllByDataset(Dataset dataset);

}
