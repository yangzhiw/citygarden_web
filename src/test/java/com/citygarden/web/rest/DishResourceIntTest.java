package com.citygarden.web.rest;

import com.citygarden.Application;
import com.citygarden.domain.Dish;
import com.citygarden.repository.DishRepository;

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
 * Test class for the DishResource REST controller.
 *
 * @see DishResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DishResourceIntTest {


    @Inject
    private DishRepository dishRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDishMockMvc;

    private Dish dish;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DishResource dishResource = new DishResource();
        ReflectionTestUtils.setField(dishResource, "dishRepository", dishRepository);
        this.restDishMockMvc = MockMvcBuilders.standaloneSetup(dishResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dishRepository.deleteAll();
        dish = new Dish();
    }

    @Test
    public void createDish() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish

        restDishMockMvc.perform(post("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isCreated());

        // Validate the Dish in the database
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeCreate + 1);
        Dish testDish = dishs.get(dishs.size() - 1);
    }

    @Test
    public void getAllDishs() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

        // Get all the dishs
        restDishMockMvc.perform(get("/api/dishs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dish.getId())));
    }

    @Test
    public void getDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

        // Get the dish
        restDishMockMvc.perform(get("/api/dishs/{id}", dish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dish.getId()));
    }

    @Test
    public void getNonExistingDish() throws Exception {
        // Get the dish
        restDishMockMvc.perform(get("/api/dishs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

		int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Update the dish

        restDishMockMvc.perform(put("/api/dishs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dish)))
                .andExpect(status().isOk());

        // Validate the Dish in the database
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeUpdate);
        Dish testDish = dishs.get(dishs.size() - 1);
    }

    @Test
    public void deleteDish() throws Exception {
        // Initialize the database
        dishRepository.save(dish);

		int databaseSizeBeforeDelete = dishRepository.findAll().size();

        // Get the dish
        restDishMockMvc.perform(delete("/api/dishs/{id}", dish.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dish> dishs = dishRepository.findAll();
        assertThat(dishs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
