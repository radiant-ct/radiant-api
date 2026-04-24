package dev.pepecoral.radiant.modules.datasets.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.dtos.DatasetCreationDTO;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.services.DatasetService;
import dev.pepecoral.radiant.modules.datasets.services.ImageService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

                when(datasetService.create(any())).thenReturn(dataset);

                when(datasetService.create(any())).thenReturn(dataset);

                mockMvc.perform(post("/datasets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datasetCreationDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.name").value(dataset.getName()));

                verify(datasetService).create(any());

        }

}