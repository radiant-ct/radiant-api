package dev.pepecoral.radiant.modules.datasets.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pepecoral.radiant.modules.common.services.FileService;
import dev.pepecoral.radiant.modules.common.services.QueueService;
import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.dtos.DatasetCreationDTO;
import dev.pepecoral.radiant.modules.datasets.dtos.ImageCreationDTO;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.services.DatasetService;
import dev.pepecoral.radiant.modules.datasets.services.ImageService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@WebMvcTest(DatasetController.class)
public class DatasetControllerTest {

        @Autowired
        MockMvc mockMvc;

        ObjectMapper objectMapper = new ObjectMapper();

        @MockitoBean
        DatasetService datasetService;

        @MockitoBean
        ImageService imageService;

        @MockitoBean
        FileService fileService;

        @MockitoBean
        QueueService queueService;

        @BeforeEach
        public void beforeEach() {
                when(fileService.saveFileToQueue(any(), any())).thenReturn(null);
        }

        @Test
        public void datasetController_shouldReturnDataset_whenAskedById() throws Exception {

                Dataset createdDataset = DatasetTestBuilder.builder().id(UUID.randomUUID()).build().entity();

                when(datasetService.findById(createdDataset.getId()))
                                .thenReturn(createdDataset);

                mockMvc.perform(get("/datasets/" + createdDataset.getId()))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$.id").value(createdDataset.getId().toString()));
        }

        @Test
        public void datasetController_shouldReturnImages_whenAskedById() throws Exception {

                Dataset dataset = DatasetTestBuilder.builder().id(UUID.randomUUID()).build().entity();

                List<Image> images = IntStream.range(0, 5)
                                .mapToObj(i -> ImageTestBuilder.builder()
                                                .dataset(dataset)
                                                .id(UUID.randomUUID())
                                                .build()
                                                .entity())
                                .toList();

                when(datasetService.findById(dataset.getId()))
                                .thenReturn(dataset);

                when(imageService.findByDataset(dataset)).thenReturn(images);

                mockMvc.perform(get("/datasets/" + dataset.getId() + "/images"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$.length()").value(5));

                verify(datasetService).findById(dataset.getId());
                verify(imageService).findByDataset(dataset);
        }

        @Test
        public void datasetController_shouldCreateDataset() throws Exception {

                Dataset dataset = DatasetTestBuilder.builder().build().entity();

                DatasetCreationDTO datasetCreationDTO = new DatasetCreationDTO(dataset.getName(),
                                dataset.getDescription(), dataset.getCredits());

                MockMultipartFile file = new MockMultipartFile(
                                "file",
                                "test.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                "hello".getBytes());

                MockMultipartFile data = new MockMultipartFile(
                                "data",
                                "",
                                MediaType.APPLICATION_JSON_VALUE,
                                objectMapper.writeValueAsBytes(datasetCreationDTO));

                when(datasetService.create(any())).thenReturn(dataset);

                when(datasetService.create(any())).thenReturn(dataset);

                when(queueService.queueDataset(any(), any())).thenReturn(dataset);

                mockMvc.perform(multipart("/datasets")
                                .file(file)
                                .file(data))
                                .andExpect(status().isCreated());

                verify(queueService).queueDataset(any(), any());

        }

        @Test
        public void datasetController_shouldCreateImage() throws Exception {

                Dataset dataset = DatasetTestBuilder.builder().id(UUID.randomUUID()).build().entity();

                Image image = ImageTestBuilder.builder().build().entity();
                ImageCreationDTO imageCreationDTO = new ImageCreationDTO(image);

                when(datasetService.findById(any())).thenReturn(dataset);
                when(imageService.create(any(), any())).thenReturn(image);

                mockMvc.perform(post("/datasets/" + dataset.getId() + "/images")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(imageCreationDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                verify(imageService).create(any(), any());

        }

}