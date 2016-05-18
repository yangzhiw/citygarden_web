package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.ProvideMerchant;
import com.citygarden.repository.ProvideMerchantRepository;
import com.citygarden.service.ProvideMerchantService;
import com.citygarden.web.rest.dto.ProvideMerchantDTO;
import com.citygarden.web.rest.mapper.ProvideMerchantMapper;

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
 * Test class for the ProvideMerchantResource REST controller.
 *
 * @see ProvideMerchantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProvideMerchantResourceIntTest {


    @Inject
    private ProvideMerchantRepository provideMerchantRepository;

    @Inject
    private ProvideMerchantMapper provideMerchantMapper;

    @Inject
    private ProvideMerchantService provideMerchantService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProvideMerchantMockMvc;

    private ProvideMerchant provideMerchant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProvideMerchantResource provideMerchantResource = new ProvideMerchantResource();
        ReflectionTestUtils.setField(provideMerchantResource, "provideMerchantService", provideMerchantService);
        ReflectionTestUtils.setField(provideMerchantResource, "provideMerchantMapper", provideMerchantMapper);
        this.restProvideMerchantMockMvc = MockMvcBuilders.standaloneSetup(provideMerchantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        provideMerchantRepository.deleteAll();
        provideMerchant = new ProvideMerchant();
    }

    @Test
    public void createProvideMerchant() throws Exception {
        int databaseSizeBeforeCreate = provideMerchantRepository.findAll().size();

        // Create the ProvideMerchant
        ProvideMerchantDTO provideMerchantDTO = provideMerchantMapper.provideMerchantToProvideMerchantDTO(provideMerchant);

        restProvideMerchantMockMvc.perform(post("/api/provideMerchants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provideMerchantDTO)))
                .andExpect(status().isCreated());

        // Validate the ProvideMerchant in the database
        List<ProvideMerchant> provideMerchants = provideMerchantRepository.findAll();
        assertThat(provideMerchants).hasSize(databaseSizeBeforeCreate + 1);
        ProvideMerchant testProvideMerchant = provideMerchants.get(provideMerchants.size() - 1);
    }

    @Test
    public void getAllProvideMerchants() throws Exception {
        // Initialize the database
        provideMerchantRepository.save(provideMerchant);

        // Get all the provideMerchants
        restProvideMerchantMockMvc.perform(get("/api/provideMerchants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(provideMerchant.getId())));
    }

    @Test
    public void getProvideMerchant() throws Exception {
        // Initialize the database
        provideMerchantRepository.save(provideMerchant);

        // Get the provideMerchant
        restProvideMerchantMockMvc.perform(get("/api/provideMerchants/{id}", provideMerchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(provideMerchant.getId()));
    }

    @Test
    public void getNonExistingProvideMerchant() throws Exception {
        // Get the provideMerchant
        restProvideMerchantMockMvc.perform(get("/api/provideMerchants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProvideMerchant() throws Exception {
        // Initialize the database
        provideMerchantRepository.save(provideMerchant);

		int databaseSizeBeforeUpdate = provideMerchantRepository.findAll().size();

        // Update the provideMerchant
        ProvideMerchantDTO provideMerchantDTO = provideMerchantMapper.provideMerchantToProvideMerchantDTO(provideMerchant);

        restProvideMerchantMockMvc.perform(put("/api/provideMerchants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provideMerchantDTO)))
                .andExpect(status().isOk());

        // Validate the ProvideMerchant in the database
        List<ProvideMerchant> provideMerchants = provideMerchantRepository.findAll();
        assertThat(provideMerchants).hasSize(databaseSizeBeforeUpdate);
        ProvideMerchant testProvideMerchant = provideMerchants.get(provideMerchants.size() - 1);
    }

    @Test
    public void deleteProvideMerchant() throws Exception {
        // Initialize the database
        provideMerchantRepository.save(provideMerchant);

		int databaseSizeBeforeDelete = provideMerchantRepository.findAll().size();

        // Get the provideMerchant
        restProvideMerchantMockMvc.perform(delete("/api/provideMerchants/{id}", provideMerchant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProvideMerchant> provideMerchants = provideMerchantRepository.findAll();
        assertThat(provideMerchants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
