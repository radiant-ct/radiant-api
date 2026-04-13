package dev.pepecoral.radiant.modules.datasets.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.pepecoral.radiant.modules.datasets.builders.DatasetTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.services.DatasetService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(DatasetController.class)
public class DatasetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DatasetService datasetService;

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
}