package dev.pepecoral.radiant.modules.common.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.services.DatasetService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final FileService fileService;
    private final DatasetService datasetService;

    public Dataset queueDataset(Dataset dataset, MultipartFile file) {

        Dataset savedDataset = datasetService.create(dataset);
        fileService.saveFileToQueue(file, savedDataset.getId().toString());

        return savedDataset;
    }

}
