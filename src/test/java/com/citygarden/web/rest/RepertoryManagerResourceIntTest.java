package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.RepertoryManager;
import com.citygarden.repository.RepertoryManagerRepository;

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
 * Test class for the RepertoryManagerResource REST controller.
 *
 * @see RepertoryManagerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RepertoryManagerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_NOW_COUNT = 1;
    private static final Integer UPDATED_NOW_COUNT = 2;

    private static final Long DEFAULT_TOTAL_COUNT = 1L;
    private static final Long UPDATED_TOTAL_COUNT = 2L;

    @Inject
    private RepertoryManagerRepository repertoryManagerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRepertoryManagerMockMvc;

    private RepertoryManager repertoryManager;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RepertoryManagerResource repertoryManagerResource = new RepertoryManagerResource();
        ReflectionTestUtils.setField(repertoryManagerResource, "repertoryManagerRepository", repertoryManagerRepository);
        this.restRepertoryManagerMockMvc = MockMvcBuilders.standaloneSetup(repertoryManagerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        repertoryManagerRepository.deleteAll();
        repertoryManager = new RepertoryManager();
        repertoryManager.setName(DEFAULT_NAME);
        repertoryManager.setNowCount(DEFAULT_NOW_COUNT);
        repertoryManager.setTotalCount(DEFAULT_TOTAL_COUNT);
    }

    @Test
    public void createRepertoryManager() throws Exception {
        int databaseSizeBeforeCreate = repertoryManagerRepository.findAll().size();

        // Create the RepertoryManager

        restRepertoryManagerMockMvc.perform(post("/api/repertoryManagers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repertoryManager)))
                .andExpect(status().isCreated());

        // Validate the RepertoryManager in the database
        List<RepertoryManager> repertoryManagers = repertoryManagerRepository.findAll();
        assertThat(repertoryManagers).hasSize(databaseSizeBeforeCreate + 1);
        RepertoryManager testRepertoryManager = repertoryManagers.get(repertoryManagers.size() - 1);
        assertThat(testRepertoryManager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRepertoryManager.getNowCount()).isEqualTo(DEFAULT_NOW_COUNT);
        assertThat(testRepertoryManager.getTotalCount()).isEqualTo(DEFAULT_TOTAL_COUNT);
    }

    @Test
    public void getAllRepertoryManagers() throws Exception {
        // Initialize the database
        repertoryManagerRepository.save(repertoryManager);

        // Get all the repertoryManagers
        restRepertoryManagerMockMvc.perform(get("/api/repertoryManagers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(repertoryManager.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].nowCount").value(hasItem(DEFAULT_NOW_COUNT)))
                .andExpect(jsonPath("$.[*].totalCount").value(hasItem(DEFAULT_TOTAL_COUNT.intValue())));
    }

    @Test
    public void getRepertoryManager() throws Exception {
        // Initialize the database
        repertoryManagerRepository.save(repertoryManager);

        // Get the repertoryManager
        restRepertoryManagerMockMvc.perform(get("/api/repertoryManagers/{id}", repertoryManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(repertoryManager.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nowCount").value(DEFAULT_NOW_COUNT))
            .andExpect(jsonPath("$.totalCount").value(DEFAULT_TOTAL_COUNT.intValue()));
    }

    @Test
    public void getNonExistingRepertoryManager() throws Exception {
        // Get the repertoryManager
        restRepertoryManagerMockMvc.perform(get("/api/repertoryManagers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateRepertoryManager() throws Exception {
        // Initialize the database
        repertoryManagerRepository.save(repertoryManager);

		int databaseSizeBeforeUpdate = repertoryManagerRepository.findAll().size();

        // Update the repertoryManager
        repertoryManager.setName(UPDATED_NAME);
        repertoryManager.setNowCount(UPDATED_NOW_COUNT);
        repertoryManager.setTotalCount(UPDATED_TOTAL_COUNT);

        restRepertoryManagerMockMvc.perform(put("/api/repertoryManagers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repertoryManager)))
                .andExpect(status().isOk());

        // Validate the RepertoryManager in the database
        List<RepertoryManager> repertoryManagers = repertoryManagerRepository.findAll();
        assertThat(repertoryManagers).hasSize(databaseSizeBeforeUpdate);
        RepertoryManager testRepertoryManager = repertoryManagers.get(repertoryManagers.size() - 1);
        assertThat(testRepertoryManager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRepertoryManager.getNowCount()).isEqualTo(UPDATED_NOW_COUNT);
        assertThat(testRepertoryManager.getTotalCount()).isEqualTo(UPDATED_TOTAL_COUNT);
    }

    @Test
    public void deleteRepertoryManager() throws Exception {
        // Initialize the database
        repertoryManagerRepository.save(repertoryManager);

		int databaseSizeBeforeDelete = repertoryManagerRepository.findAll().size();

        // Get the repertoryManager
        restRepertoryManagerMockMvc.perform(delete("/api/repertoryManagers/{id}", repertoryManager.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RepertoryManager> repertoryManagers = repertoryManagerRepository.findAll();
        assertThat(repertoryManagers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
