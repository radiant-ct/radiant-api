package dev.pepecoral.radiant.modules.datasets.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.pepecoral.radiant.modules.datasets.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
