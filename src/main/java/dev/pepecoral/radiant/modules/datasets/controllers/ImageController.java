package dev.pepecoral.radiant.modules.datasets.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pepecoral.radiant.modules.datasets.dtos.ImageResponseDTO;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.services.ImageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/images")
@Validated
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageResponseDTO> getImageById(@PathVariable UUID imageId) {

        Image image = imageService.findById(imageId);
        ImageResponseDTO imageResponseDTO = new ImageResponseDTO(image);
        return ResponseEntity.ok(imageResponseDTO);
    }

}
