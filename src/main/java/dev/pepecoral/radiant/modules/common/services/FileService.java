package dev.pepecoral.radiant.modules.common.services;

import dev.pepecoral.radiant.modules.common.config.FileStorageProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileService {

    private final Path uploadPath;

    public FileService(FileStorageProperties properties) {
        this.uploadPath = Paths.get(properties.getQueueDir()).toAbsolutePath().normalize();

        // try {
        // Files.createDirectories(this.uploadPath);
        // } catch (IOException e) {
        // throw new RuntimeException("Could not create upload directory", e);
        // }
    }

    public String saveFileToQueue(MultipartFile file, String filename) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        try {
            Path targetPath = uploadPath.resolve(filename).normalize();

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return targetPath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}