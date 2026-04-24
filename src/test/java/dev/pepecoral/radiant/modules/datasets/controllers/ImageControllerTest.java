package dev.pepecoral.radiant.modules.datasets.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.pepecoral.radiant.modules.datasets.builders.ImageTestBuilder;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.services.ImageService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(ImageController.class)
public class ImageControllerTest {

        @Autowired
        MockMvc mockMvc;

        @MockitoBean
        ImageService imageService;

        @Test
        public void imageController_shouldReturnImage_whenAskedById() throws Exception {

                Image image = ImageTestBuilder.builder().id(UUID.randomUUID()).build().entity();

                when(imageService.findById(image.getId()))
                                .thenReturn(image);

                mockMvc.perform(get("/images/" + image.getId()))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$.id").value(image.getId().toString()));
        }

}