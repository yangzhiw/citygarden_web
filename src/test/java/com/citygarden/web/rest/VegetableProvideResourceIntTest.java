package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.VegetableProvide;
import com.citygarden.repository.VegetableProvideRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the VegetableProvideResource REST controller.
 *
 * @see VegetableProvideResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VegetableProvideResourceIntTest {


    @Inject
    private VegetableProvideRepository vegetableProvideRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVegetableProvideMockMvc;

    private VegetableProvide vegetableProvide;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VegetableProvideResource vegetableProvideResource = new VegetableProvideResource();
        ReflectionTestUtils.setField(vegetableProvideResource, "vegetableProvideRepository", vegetableProvideRepository);
        this.restVegetableProvideMockMvc = MockMvcBuilders.standaloneSetup(vegetableProvideResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vegetableProvideRepository.deleteAll();
        vegetableProvide = new VegetableProvide();
    }

    @Test
    public void createVegetableProvide() throws Exception {
        int databaseSizeBeforeCreate = vegetableProvideRepository.findAll().size();

        // Create the VegetableProvide

        restVegetableProvideMockMvc.perform(post("/api/vegetableProvides")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vegetableProvide)))
                .andExpect(status().isCreated());

        // Validate the VegetableProvide in the database
        List<VegetableProvide> vegetableProvides = vegetableProvideRepository.findAll();
        assertThat(vegetableProvides).hasSize(databaseSizeBeforeCreate + 1);
        VegetableProvide testVegetableProvide = vegetableProvides.get(vegetableProvides.size() - 1);
    }

    @Test
    public void getAllVegetableProvides() throws Exception {
        // Initialize the database
        vegetableProvideRepository.save(vegetableProvide);

        // Get all the vegetableProvides
        restVegetableProvideMockMvc.perform(get("/api/vegetableProvides?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vegetableProvide.getId())));
    }

    @Test
    public void getVegetableProvide() throws Exception {
        // Initialize the database
        vegetableProvideRepository.save(vegetableProvide);

        // Get the vegetableProvide
        restVegetableProvideMockMvc.perform(get("/api/vegetableProvides/{id}", vegetableProvide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(vegetableProvide.getId()));
    }

    @Test
    public void getNonExistingVegetableProvide() throws Exception {
        // Get the vegetableProvide
        restVegetableProvideMockMvc.perform(get("/api/vegetableProvides/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateVegetableProvide() throws Exception {
        // Initialize the database
        vegetableProvideRepository.save(vegetableProvide);

		int databaseSizeBeforeUpdate = vegetableProvideRepository.findAll().size();

        // Update the vegetableProvide

        restVegetableProvideMockMvc.perform(put("/api/vegetableProvides")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vegetableProvide)))
                .andExpect(status().isOk());

        // Validate the VegetableProvide in the database
        List<VegetableProvide> vegetableProvides = vegetableProvideRepository.findAll();
        assertThat(vegetableProvides).hasSize(databaseSizeBeforeUpdate);
        VegetableProvide testVegetableProvide = vegetableProvides.get(vegetableProvides.size() - 1);
    }

    @Test
    public void deleteVegetableProvide() throws Exception {
        // Initialize the database
        vegetableProvideRepository.save(vegetableProvide);

		int databaseSizeBeforeDelete = vegetableProvideRepository.findAll().size();

        // Get the vegetableProvide
        restVegetableProvideMockMvc.perform(delete("/api/vegetableProvides/{id}", vegetableProvide.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VegetableProvide> vegetableProvides = vegetableProvideRepository.findAll();
        assertThat(vegetableProvides).hasSize(databaseSizeBeforeDelete - 1);
    }
}
