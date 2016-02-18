package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.GroupPurchase;
import com.citygarden.repository.GroupPurchaseRepository;

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
 * Test class for the GroupPurchaseResource REST controller.
 *
 * @see GroupPurchaseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GroupPurchaseResourceIntTest {


    @Inject
    private GroupPurchaseRepository groupPurchaseRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGroupPurchaseMockMvc;

    private GroupPurchase groupPurchase;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupPurchaseResource groupPurchaseResource = new GroupPurchaseResource();
        ReflectionTestUtils.setField(groupPurchaseResource, "groupPurchaseRepository", groupPurchaseRepository);
        this.restGroupPurchaseMockMvc = MockMvcBuilders.standaloneSetup(groupPurchaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        groupPurchaseRepository.deleteAll();
        groupPurchase = new GroupPurchase();
    }

    @Test
    public void createGroupPurchase() throws Exception {
        int databaseSizeBeforeCreate = groupPurchaseRepository.findAll().size();

        // Create the GroupPurchase

        restGroupPurchaseMockMvc.perform(post("/api/groupPurchases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupPurchase)))
                .andExpect(status().isCreated());

        // Validate the GroupPurchase in the database
        List<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll();
        assertThat(groupPurchases).hasSize(databaseSizeBeforeCreate + 1);
        GroupPurchase testGroupPurchase = groupPurchases.get(groupPurchases.size() - 1);
    }

    @Test
    public void getAllGroupPurchases() throws Exception {
        // Initialize the database
        groupPurchaseRepository.save(groupPurchase);

        // Get all the groupPurchases
        restGroupPurchaseMockMvc.perform(get("/api/groupPurchases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(groupPurchase.getId())));
    }

    @Test
    public void getGroupPurchase() throws Exception {
        // Initialize the database
        groupPurchaseRepository.save(groupPurchase);

        // Get the groupPurchase
        restGroupPurchaseMockMvc.perform(get("/api/groupPurchases/{id}", groupPurchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(groupPurchase.getId()));
    }

    @Test
    public void getNonExistingGroupPurchase() throws Exception {
        // Get the groupPurchase
        restGroupPurchaseMockMvc.perform(get("/api/groupPurchases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateGroupPurchase() throws Exception {
        // Initialize the database
        groupPurchaseRepository.save(groupPurchase);

		int databaseSizeBeforeUpdate = groupPurchaseRepository.findAll().size();

        // Update the groupPurchase

        restGroupPurchaseMockMvc.perform(put("/api/groupPurchases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupPurchase)))
                .andExpect(status().isOk());

        // Validate the GroupPurchase in the database
        List<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll();
        assertThat(groupPurchases).hasSize(databaseSizeBeforeUpdate);
        GroupPurchase testGroupPurchase = groupPurchases.get(groupPurchases.size() - 1);
    }

    @Test
    public void deleteGroupPurchase() throws Exception {
        // Initialize the database
        groupPurchaseRepository.save(groupPurchase);

		int databaseSizeBeforeDelete = groupPurchaseRepository.findAll().size();

        // Get the groupPurchase
        restGroupPurchaseMockMvc.perform(delete("/api/groupPurchases/{id}", groupPurchase.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll();
        assertThat(groupPurchases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
