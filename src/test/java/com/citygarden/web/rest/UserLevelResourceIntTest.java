package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.UserLevel;
import com.citygarden.repository.UserLevelRepository;

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
 * Test class for the UserLevelResource REST controller.
 *
 * @see UserLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserLevelResourceIntTest {


    @Inject
    private UserLevelRepository userLevelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserLevelMockMvc;

    private UserLevel userLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserLevelResource userLevelResource = new UserLevelResource();
        ReflectionTestUtils.setField(userLevelResource, "userLevelRepository", userLevelRepository);
        this.restUserLevelMockMvc = MockMvcBuilders.standaloneSetup(userLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userLevelRepository.deleteAll();
        userLevel = new UserLevel();
    }

    @Test
    public void createUserLevel() throws Exception {
        int databaseSizeBeforeCreate = userLevelRepository.findAll().size();

        // Create the UserLevel

        restUserLevelMockMvc.perform(post("/api/userLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userLevel)))
                .andExpect(status().isCreated());

        // Validate the UserLevel in the database
        List<UserLevel> userLevels = userLevelRepository.findAll();
        assertThat(userLevels).hasSize(databaseSizeBeforeCreate + 1);
        UserLevel testUserLevel = userLevels.get(userLevels.size() - 1);
    }

    @Test
    public void getAllUserLevels() throws Exception {
        // Initialize the database
        userLevelRepository.save(userLevel);

        // Get all the userLevels
        restUserLevelMockMvc.perform(get("/api/userLevels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userLevel.getId())));
    }

    @Test
    public void getUserLevel() throws Exception {
        // Initialize the database
        userLevelRepository.save(userLevel);

        // Get the userLevel
        restUserLevelMockMvc.perform(get("/api/userLevels/{id}", userLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userLevel.getId()));
    }

    @Test
    public void getNonExistingUserLevel() throws Exception {
        // Get the userLevel
        restUserLevelMockMvc.perform(get("/api/userLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserLevel() throws Exception {
        // Initialize the database
        userLevelRepository.save(userLevel);

		int databaseSizeBeforeUpdate = userLevelRepository.findAll().size();

        // Update the userLevel

        restUserLevelMockMvc.perform(put("/api/userLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userLevel)))
                .andExpect(status().isOk());

        // Validate the UserLevel in the database
        List<UserLevel> userLevels = userLevelRepository.findAll();
        assertThat(userLevels).hasSize(databaseSizeBeforeUpdate);
        UserLevel testUserLevel = userLevels.get(userLevels.size() - 1);
    }

    @Test
    public void deleteUserLevel() throws Exception {
        // Initialize the database
        userLevelRepository.save(userLevel);

		int databaseSizeBeforeDelete = userLevelRepository.findAll().size();

        // Get the userLevel
        restUserLevelMockMvc.perform(delete("/api/userLevels/{id}", userLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserLevel> userLevels = userLevelRepository.findAll();
        assertThat(userLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
