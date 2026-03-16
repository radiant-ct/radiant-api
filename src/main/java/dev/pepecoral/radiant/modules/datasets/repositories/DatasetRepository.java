package dev.pepecoral.radiant.modules.datasets.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;

public interface DatasetRepository extends JpaRepository<Dataset, UUID> {

}
