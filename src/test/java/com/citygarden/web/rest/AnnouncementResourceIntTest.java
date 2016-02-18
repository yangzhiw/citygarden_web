package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.Announcement;
import com.citygarden.repository.AnnouncementRepository;

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
 * Test class for the AnnouncementResource REST controller.
 *
 * @see AnnouncementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnnouncementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private AnnouncementRepository announcementRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAnnouncementMockMvc;

    private Announcement announcement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnnouncementResource announcementResource = new AnnouncementResource();
        ReflectionTestUtils.setField(announcementResource, "announcementRepository", announcementRepository);
        this.restAnnouncementMockMvc = MockMvcBuilders.standaloneSetup(announcementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        announcementRepository.deleteAll();
        announcement = new Announcement();
        announcement.setName(DEFAULT_NAME);
    }

    @Test
    public void createAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // Create the Announcement

        restAnnouncementMockMvc.perform(post("/api/announcements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(announcement)))
                .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcements = announcementRepository.findAll();
        assertThat(announcements).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncement = announcements.get(announcements.size() - 1);
        assertThat(testAnnouncement.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementRepository.save(announcement);

        // Get all the announcements
        restAnnouncementMockMvc.perform(get("/api/announcements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.save(announcement);

        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(announcement.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingAnnouncement() throws Exception {
        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.save(announcement);

		int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement
        announcement.setName(UPDATED_NAME);

        restAnnouncementMockMvc.perform(put("/api/announcements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(announcement)))
                .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcements = announcementRepository.findAll();
        assertThat(announcements).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcements.get(announcements.size() - 1);
        assertThat(testAnnouncement.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void deleteAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.save(announcement);

		int databaseSizeBeforeDelete = announcementRepository.findAll().size();

        // Get the announcement
        restAnnouncementMockMvc.perform(delete("/api/announcements/{id}", announcement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Announcement> announcements = announcementRepository.findAll();
        assertThat(announcements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
